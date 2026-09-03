package com.yogaflow.data.model

import androidx.room.Entity

@Entity(tableName = "favorites", primaryKeys = ["userId", "poseSlug"])
data class FavoritePose(
    val userId: String,
    val poseSlug: String,
    val addedAt: Long = System.currentTimeMillis()
)
