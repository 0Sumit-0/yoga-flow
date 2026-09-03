package com.yogaflow.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_completions")
data class PracticeCompletion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val completedDate: String, // ISO date format "YYYY-MM-DD"
    val planTarget: String, // "Digestion", "Mental Focus", "Sleep", "Energy", "Stress Relief", "Flexibility", "Custom"
    val durationMinutes: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val notes: String = ""
)
