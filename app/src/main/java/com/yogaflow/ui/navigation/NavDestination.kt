package com.yogaflow.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Plans : Screen("plans")
    object PlanDetail : Screen("plan/{target}") {
        fun createRoute(target: String) = "plan/$target"
    }
    object Poses : Screen("poses")
    object PoseDetail : Screen("pose/{slug}") {
        fun createRoute(slug: String) = "pose/$slug"
    }
    object Favorites : Screen("favorites")
    object Progress : Screen("progress")
    object Onboarding : Screen("onboarding")
    object Auth : Screen("auth")
    object Profile : Screen("profile")
    object PracticePlayer : Screen("practice/{planId}") {
        fun createRoute(planId: String) = "practice/$planId"
    }
}
