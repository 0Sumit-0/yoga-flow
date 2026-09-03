package com.yogaflow.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poses")
data class YogaPose(
    @PrimaryKey val slug: String,
    val name: String,
    val sanskritName: String,
    val category: String, // Seated, Standing, Backbend, Inversion, Restorative, Forward Bend, Balancing, Twist
    val targets: List<String>, // Digestion, Mental Focus, Sleep, Energy, Stress Relief, Flexibility
    val difficulty: String, // Beginner, Intermediate, Advanced
    val durationSeconds: Int, // e.g. 60
    val instructions: List<String>,
    val benefits: List<String>,
    val breathingCue: String,
    val modifications: String = "",
    val iconType: String = "meditation"
)
