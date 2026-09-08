package com.yogaflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yogaflow.data.model.FavoritePose
import com.yogaflow.data.model.PlanPoseItem
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.data.model.UserProfile
import com.yogaflow.data.model.YogaPose
import com.yogaflow.data.model.canonicalizePoseSlug
import com.yogaflow.data.repository.StreakStats
import com.yogaflow.data.repository.YogaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ActivePracticeState(
    val planId: String = "",
    val title: String = "",
    val target: String = "",
    val poseItems: List<PlanPoseItem> = emptyList(),
    val currentPoseIndex: Int = 0,
    val secondsRemaining: Int = 60,
    val totalPoseDuration: Int = 60,
    val isPlaying: Boolean = false,
    val isFinished: Boolean = false,
    val totalElapsedSeconds: Int = 0
)

class YogaViewModel(private val repository: YogaRepository) : ViewModel() {

    // Current active user ID
    private val _currentUserId = MutableStateFlow("user_default")
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

    // All registered profiles
    val allProfiles: StateFlow<List<UserProfile>?> = repository.getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Active User Profile
    val userProfile: StateFlow<UserProfile?> = _currentUserId
        .flatMapLatest { userId -> repository.getProfile(userId) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // All Poses Library
    val allPoses: StateFlow<List<YogaPose>> = repository.allPoses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Pose Library Search & Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow("All") // All, Beginner, Intermediate, Advanced
    val selectedDifficulty: StateFlow<String> = _selectedDifficulty.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All") // All, Seated, Standing, Backbend, Inversion, Restorative, Forward Bend, Balancing, Twist
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedTargetFilter = MutableStateFlow("All") // All, Digestion, Mental Focus, Sleep, Energy, Stress Relief, Flexibility
    val selectedTargetFilter: StateFlow<String> = _selectedTargetFilter.asStateFlow()

    // Filtered Poses
    val filteredPoses: StateFlow<List<YogaPose>> = combine(
        allPoses,
        _searchQuery,
        _selectedDifficulty,
        _selectedCategory,
        _selectedTargetFilter
    ) { poses, query, difficulty, category, target ->
        poses.filter { pose ->
            val matchesQuery = query.isBlank() ||
                    pose.name.contains(query, ignoreCase = true) ||
                    pose.sanskritName.contains(query, ignoreCase = true) ||
                    pose.targets.any { it.contains(query, ignoreCase = true) }

            val matchesDifficulty = difficulty == "All" || pose.difficulty.equals(difficulty, ignoreCase = true)
            val matchesCategory = category == "All" || pose.category.equals(category, ignoreCase = true)
            val matchesTarget = target == "All" || pose.targets.any { it.equals(target, ignoreCase = true) }

            matchesQuery && matchesDifficulty && matchesCategory && matchesTarget
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Target Plans
    val plans: List<TargetPlan> = repository.getAllPlans()

    // Favorites for current user
    val favorites: StateFlow<List<FavoritePose>> = _currentUserId
        .flatMapLatest { userId -> repository.getFavorites(userId) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Favorited YogaPoses (for Custom Plan)
    val favoritePoses: StateFlow<List<YogaPose>> = combine(allPoses, favorites) { poses, favs ->
        val favSlugs = favs.map { it.poseSlug }.toSet()
        poses.filter { it.slug in favSlugs }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Practice Completions for current user
    val completions: StateFlow<List<PracticeCompletion>> = _currentUserId
        .flatMapLatest { userId -> repository.getCompletions(userId) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Calculated Streak Statistics
    val streakStats: StateFlow<StreakStats> = completions
        .flatMapLatest { compList -> flowOf(repository.calculateStreak(compList)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StreakStats())

    // Practice Player State
    private val _practiceState = MutableStateFlow<ActivePracticeState?>(null)
    val practiceState: StateFlow<ActivePracticeState?> = _practiceState.asStateFlow()

    private var timerJob: Job? = null

    // Set Search & Filters
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setDifficultyFilter(difficulty: String) {
        _selectedDifficulty.value = difficulty
    }

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
    }

    fun setTargetFilter(target: String) {
        _selectedTargetFilter.value = target
    }

    // Toggle Favorite
    fun toggleFavorite(poseSlug: String) {
        val currentFavs = favorites.value.map { it.poseSlug }
        val isFav = currentFavs.contains(poseSlug)
        viewModelScope.launch {
            repository.toggleFavorite(_currentUserId.value, poseSlug, isFav)
        }
    }

    // Profile Management & Auth
    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.saveProfile(profile)
            _currentUserId.value = profile.id
        }
    }

    fun switchUser(userId: String) {
        _currentUserId.value = userId
    }

    fun signInOrCreateUser(email: String, name: String) {
        val userId = if (email.isNotBlank()) "user_${email.replace("[^a-zA-Z0-9]".toRegex(), "_")}" else "user_${System.currentTimeMillis()}"
        viewModelScope.launch {
            val existing = allProfiles.value?.firstOrNull { it.id == userId || (it.email.isNotBlank() && it.email == email) }
            if (existing != null) {
                _currentUserId.value = existing.id
            } else {
                val newProfile = UserProfile(
                    id = userId,
                    email = email,
                    name = name.ifBlank { "Yoga Practitioner" },
                    createdAt = System.currentTimeMillis()
                )
                repository.saveProfile(newProfile)
                _currentUserId.value = userId
            }
        }
    }

    // Practice Flow Player Actions
    fun startPracticePlan(plan: TargetPlan) {
        timerJob?.cancel()
        val poseItems = plan.poses.map { item ->
            item.copy(poseSlug = canonicalizePoseSlug(item.poseSlug) ?: item.poseSlug)
        }
        val firstPoseDuration = poseItems.firstOrNull()?.durationSeconds ?: 60
        _practiceState.value = ActivePracticeState(
            planId = plan.id,
            title = plan.title,
            target = plan.target,
            poseItems = poseItems,
            currentPoseIndex = 0,
            secondsRemaining = firstPoseDuration,
            totalPoseDuration = firstPoseDuration,
            isPlaying = true,
            isFinished = false,
            totalElapsedSeconds = 0
        )
        startTimerLoop()
    }

    fun startCustomFavoritesPractice() {
        val favs = favoritePoses.value
        if (favs.isEmpty()) return

        val poseItems = favs.map { PlanPoseItem(it.slug, it.durationSeconds) }
        val firstDuration = poseItems.first().durationSeconds

        timerJob?.cancel()
        _practiceState.value = ActivePracticeState(
            planId = "custom_favorites",
            title = "Personal Flow",
            target = "Custom Plan",
            poseItems = poseItems,
            currentPoseIndex = 0,
            secondsRemaining = firstDuration,
            totalPoseDuration = firstDuration,
            isPlaying = true,
            isFinished = false,
            totalElapsedSeconds = 0
        )
        startTimerLoop()
    }

    fun startSinglePosePractice(pose: YogaPose) {
        timerJob?.cancel()
        _practiceState.value = ActivePracticeState(
            planId = "single_${pose.slug}",
            title = pose.name,
            target = pose.targets.firstOrNull() ?: "Practice",
            poseItems = listOf(PlanPoseItem(pose.slug, pose.durationSeconds)),
            currentPoseIndex = 0,
            secondsRemaining = pose.durationSeconds,
            totalPoseDuration = pose.durationSeconds,
            isPlaying = true,
            isFinished = false,
            totalElapsedSeconds = 0
        )
        startTimerLoop()
    }

    fun togglePlayPause() {
        val current = _practiceState.value ?: return
        val newPlaying = !current.isPlaying
        _practiceState.value = current.copy(isPlaying = newPlaying)
        if (newPlaying) {
            startTimerLoop()
        } else {
            timerJob?.cancel()
        }
    }

    fun nextPose() {
        val current = _practiceState.value ?: return
        if (current.currentPoseIndex < current.poseItems.size - 1) {
            val nextIndex = current.currentPoseIndex + 1
            val nextDuration = current.poseItems[nextIndex].durationSeconds
            _practiceState.value = current.copy(
                currentPoseIndex = nextIndex,
                secondsRemaining = nextDuration,
                totalPoseDuration = nextDuration
            )
        } else {
            finishPractice()
        }
    }

    fun prevPose() {
        val current = _practiceState.value ?: return
        if (current.currentPoseIndex > 0) {
            val prevIndex = current.currentPoseIndex - 1
            val prevDuration = current.poseItems[prevIndex].durationSeconds
            _practiceState.value = current.copy(
                currentPoseIndex = prevIndex,
                secondsRemaining = prevDuration,
                totalPoseDuration = prevDuration
            )
        }
    }

    fun finishPractice(notes: String = "") {
        timerJob?.cancel()
        val current = _practiceState.value ?: return
        val minutesPracticed = maxOf(1, (current.totalElapsedSeconds + 30) / 60)

        viewModelScope.launch {
            repository.logCompletion(
                userId = _currentUserId.value,
                planTarget = current.target,
                durationMinutes = minutesPracticed,
                notes = if (notes.isNotBlank()) notes else "Completed ${current.title}"
            )
        }

        _practiceState.value = current.copy(
            isPlaying = false,
            isFinished = true
        )
    }

    fun resetPracticeState() {
        timerJob?.cancel()
        _practiceState.value = null
    }

    private fun startTimerLoop() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _practiceState.value ?: break
                if (!current.isPlaying || current.isFinished) break

                if (current.secondsRemaining > 1) {
                    _practiceState.value = current.copy(
                        secondsRemaining = current.secondsRemaining - 1,
                        totalElapsedSeconds = current.totalElapsedSeconds + 1
                    )
                } else {
                    // Pose completed! Advance to next pose or finish
                    val nextIndex = current.currentPoseIndex + 1
                    if (nextIndex < current.poseItems.size) {
                        val nextDuration = current.poseItems[nextIndex].durationSeconds
                        _practiceState.value = current.copy(
                            currentPoseIndex = nextIndex,
                            secondsRemaining = nextDuration,
                            totalPoseDuration = nextDuration,
                            totalElapsedSeconds = current.totalElapsedSeconds + 1
                        )
                    } else {
                        finishPractice()
                        break
                    }
                }
            }
        }
    }

    // Manual Log Practice
    fun logManualPractice(target: String, durationMinutes: Int, notes: String) {
        viewModelScope.launch {
            repository.logCompletion(
                userId = _currentUserId.value,
                planTarget = target,
                durationMinutes = durationMinutes,
                notes = notes
            )
        }
    }

    fun deleteCompletion(id: Long) {
        viewModelScope.launch {
            repository.deleteCompletion(id)
        }
    }
}

class YogaViewModelFactory(private val repository: YogaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YogaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
