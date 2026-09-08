package com.yogaflow.data.model
fun canonicalizePoseSlug(raw: String?): String? {
    if (raw.isNullOrBlank()) return raw
    val snake = raw.trim().lowercase().replace('-', '_')
    val withPose = if (snake.endsWith("_pose")) snake else "${snake}_pose"
    return POSE_SLUG_ALIASES[snake] ?: POSE_SLUG_ALIASES[withPose] ?: withPose
}
fun List<YogaPose>.findBySlug(raw: String?): YogaPose? {
    if (raw.isNullOrBlank()) return null
    firstOrNull { it.slug == raw }?.let { return it }
    val canonical = canonicalizePoseSlug(raw) ?: return null
    return firstOrNull { it.slug == canonical }
}
fun mapGoalToPlanTarget(goal: String): String = when {
    goal.equals("Deep Sleep", ignoreCase = true) ||
            goal.equals("Better Sleep", ignoreCase = true) ||
            goal.equals("Sleep", ignoreCase = true) -> "Better Sleep"
    else -> goal
}
private val POSE_SLUG_ALIASES = mapOf(
    "childs_pose" to "puppy_pose",
    "childs" to "puppy_pose",
    "cat_cow_stretch" to "seated_mountain_pose",
    "cat_cow_stretch_pose" to "seated_mountain_pose",
    "cat_cow" to "seated_mountain_pose",
    "seated_forward_bend" to "head_to_knee_forward_bend_pose",
    "seated_forward_bend_pose" to "head_to_knee_forward_bend_pose",
    "warrior_two" to "warrior_ii_pose",
    "warrior_two_pose" to "warrior_ii_pose",
    "warrior_2" to "warrior_ii_pose",
    "warrior_ii" to "warrior_ii_pose",
    "pigeon_pose" to "king_pigeon_pose",
    "pigeon" to "king_pigeon_pose",
    "happy_baby_pose" to "knees_to_chest_pose",
    "happy_baby" to "knees_to_chest_pose",
    "supine_spinal_twist" to "seated_spinal_twist_pose",
    "supine_spinal_twist_pose" to "seated_spinal_twist_pose",
    "downward_facing_dog" to "downward_facing_dog_pose",
    "legs_up_the_wall" to "legs_up_the_wall_pose",
    "triangle" to "triangle_pose",
    "tree" to "tree_pose",
    "cobra" to "cobra_pose",
    "bridge" to "bridge_pose",
    "camel" to "camel_pose",
    "corpse" to "corpse_pose",
    "puppy" to "puppy_pose"
)