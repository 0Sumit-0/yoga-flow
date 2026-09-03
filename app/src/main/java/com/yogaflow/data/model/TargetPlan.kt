package com.yogaflow.data.model

data class PlanPoseItem(
    val poseSlug: String,
    val durationSeconds: Int,
    val side: String? = null // "Both", "Left Side", "Right Side"
)

data class TargetPlan(
    val id: String,
    val target: String, // "Digestion", "Mental Focus", "Sleep", "Energy", "Stress Relief", "Flexibility"
    val title: String,
    val subtitle: String,
    val description: String,
    val level: String,
    val estimatedMinutes: Int,
    val poses: List<PlanPoseItem>,
    val highlights: List<String>
)
