package com.yogaflow.data.repository

import com.yogaflow.data.db.InitialData
import com.yogaflow.data.db.YogaDao
import com.yogaflow.data.model.FavoritePose
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.data.model.UserProfile
import com.yogaflow.data.model.YogaPose
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class StreakStats(
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val totalPractices: Int = 0,
    val totalMinutes: Int = 0,
    val practicedToday: Boolean = false,
    val completedDatesSet: Set<String> = emptySet()
)

class YogaRepository(private val dao: YogaDao) {

    val allPoses: Flow<List<YogaPose>> = dao.getAllPoses()

    fun getProfile(userId: String): Flow<UserProfile?> = dao.getProfile(userId)
    fun getFirstProfile(): Flow<UserProfile?> = dao.getFirstProfile()
    fun getAllProfiles(): Flow<List<UserProfile>> = dao.getAllProfiles()

    suspend fun saveProfile(profile: UserProfile) {
        dao.saveProfile(profile)
    }

    suspend fun deleteProfile(userId: String) {
        dao.deleteProfile(userId)
    }

    suspend fun getPoseBySlug(slug: String): YogaPose? = dao.getPoseBySlug(slug)

    fun getPoseBySlugFlow(slug: String): Flow<YogaPose?> = dao.getPoseBySlugFlow(slug)

    fun getAllPlans(): List<TargetPlan> = InitialData.targetPlans

    fun getPlanById(id: String): TargetPlan? {
        return InitialData.targetPlans.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }

    fun getPlanByTarget(target: String): TargetPlan? {
        return InitialData.targetPlans.firstOrNull { it.target.equals(target, ignoreCase = true) }
    }

    fun getFavorites(userId: String): Flow<List<FavoritePose>> = dao.getFavorites(userId)

    fun isFavorite(userId: String, poseSlug: String): Flow<Boolean> = dao.isFavorite(userId, poseSlug)

    suspend fun toggleFavorite(userId: String, poseSlug: String, currentlyFavorite: Boolean) {
        if (currentlyFavorite) {
            dao.removeFavorite(userId, poseSlug)
        } else {
            dao.addFavorite(FavoritePose(userId = userId, poseSlug = poseSlug, addedAt = System.currentTimeMillis()))
        }
    }

    fun getCompletions(userId: String): Flow<List<PracticeCompletion>> = dao.getCompletions(userId)

    suspend fun logCompletion(
        userId: String,
        planTarget: String,
        durationMinutes: Int,
        notes: String = ""
    ): PracticeCompletion {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(Date())
        val completion = PracticeCompletion(
            userId = userId,
            completedDate = todayStr,
            planTarget = planTarget,
            durationMinutes = durationMinutes,
            timestamp = System.currentTimeMillis(),
            notes = notes
        )
        dao.logCompletion(completion)
        return completion
    }

    suspend fun deleteCompletion(completionId: Long) {
        dao.deleteCompletion(completionId)
    }

    fun calculateStreak(completions: List<PracticeCompletion>): StreakStats {
        if (completions.isEmpty()) {
            return StreakStats()
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val datesSet = completions.map { it.completedDate }.toSet()
        val totalMinutes = completions.sumOf { it.durationMinutes }

        val today = Calendar.getInstance()
        val todayStr = sdf.format(today.time)
        val practicedToday = datesSet.contains(todayStr)

        // Calculate current streak
        var currentStreak = 0
        val checkCal = Calendar.getInstance()

        // If practiced today, start counting from today. Otherwise, check if practiced yesterday to keep streak alive
        val yesterdayCal = Calendar.getInstance()
        yesterdayCal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayStr = sdf.format(yesterdayCal.time)

        val startFromToday = datesSet.contains(todayStr)
        val startFromYesterday = datesSet.contains(yesterdayStr)

        if (startFromToday || startFromYesterday) {
            if (!startFromToday) {
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            }
            while (datesSet.contains(sdf.format(checkCal.time))) {
                currentStreak++
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            }
        }

        // Calculate best streak historically
        val sortedDates = datesSet.mapNotNull {
            try {
                sdf.parse(it)
            } catch (e: Exception) {
                null
            }
        }.sorted()

        var bestStreak = 0
        var tempStreak = 0
        var prevDate: Date? = null

        for (date in sortedDates) {
            if (prevDate == null) {
                tempStreak = 1
            } else {
                val diffDays = ((date.time - prevDate.time) / (1000 * 60 * 60 * 24)).toInt()
                if (diffDays == 1) {
                    tempStreak++
                } else if (diffDays > 1) {
                    tempStreak = 1
                }
            }
            if (tempStreak > bestStreak) {
                bestStreak = tempStreak
            }
            prevDate = date
        }

        if (currentStreak > bestStreak) {
            bestStreak = currentStreak
        }

        return StreakStats(
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            totalPractices = completions.size,
            totalMinutes = totalMinutes,
            practicedToday = practicedToday,
            completedDatesSet = datesSet
        )
    }
}
