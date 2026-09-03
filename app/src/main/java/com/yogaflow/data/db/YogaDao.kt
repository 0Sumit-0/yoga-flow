package com.yogaflow.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogaflow.data.model.FavoritePose
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.model.UserProfile
import com.yogaflow.data.model.YogaPose
import kotlinx.coroutines.flow.Flow

@Dao
interface YogaDao {
    // Profiles
    @Query("SELECT * FROM profiles WHERE id = :userId LIMIT 1")
    fun getProfile(userId: String): Flow<UserProfile?>

    @Query("SELECT * FROM profiles LIMIT 1")
    fun getFirstProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM profiles")
    fun getAllProfiles(): Flow<List<UserProfile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: UserProfile)

    @Query("DELETE FROM profiles WHERE id = :userId")
    suspend fun deleteProfile(userId: String)

    // Poses
    @Query("SELECT * FROM poses ORDER BY name ASC")
    fun getAllPoses(): Flow<List<YogaPose>>

    @Query("SELECT * FROM poses WHERE slug = :slug LIMIT 1")
    suspend fun getPoseBySlug(slug: String): YogaPose?

    @Query("SELECT * FROM poses WHERE slug = :slug LIMIT 1")
    fun getPoseBySlugFlow(slug: String): Flow<YogaPose?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoses(poses: List<YogaPose>)

    // Favorites
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY addedAt DESC")
    fun getFavorites(userId: String): Flow<List<FavoritePose>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND poseSlug = :poseSlug)")
    fun isFavorite(userId: String, poseSlug: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoritePose)

    @Query("DELETE FROM favorites WHERE userId = :userId AND poseSlug = :poseSlug")
    suspend fun removeFavorite(userId: String, poseSlug: String)

    // Practice Completions
    @Query("SELECT * FROM practice_completions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getCompletions(userId: String): Flow<List<PracticeCompletion>>

    @Query("SELECT * FROM practice_completions WHERE userId = :userId AND completedDate = :date")
    fun getCompletionsForDate(userId: String, date: String): Flow<List<PracticeCompletion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun logCompletion(completion: PracticeCompletion)

    @Query("DELETE FROM practice_completions WHERE id = :completionId")
    suspend fun deleteCompletion(completionId: Long)
}
