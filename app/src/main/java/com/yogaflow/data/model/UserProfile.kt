package com.yogaflow.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class UserProfile(
    @PrimaryKey val id: String = "default_user",
    val email: String = "",
    val name: String = "Practitioner",
    val age: Int = 28,
    val gender: String = "Not specified",
    val level: String = "Beginner", // Beginner, Intermediate, Advanced
    val goals: List<String> = listOf("Stress Relief", "Flexibility"), // Digestion, Mental Focus, Sleep, Energy, Stress Relief, Flexibility
    val createdAt: Long = System.currentTimeMillis()
)
