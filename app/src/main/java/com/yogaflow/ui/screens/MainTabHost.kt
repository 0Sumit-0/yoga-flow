package com.yogaflow.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.ui.navigation.Screen
import com.yogaflow.ui.viewmodel.YogaViewModel
@Composable
fun MainTabHost(
    selectedTab: String,
    viewModel: YogaViewModel,
    onNavigateToPlan: (String) -> Unit,
    onSelectTab: (String) -> Unit,
    onNavigateToProfile: () -> Unit,
    onStartPracticePlan: (TargetPlan) -> Unit,
    onNavigateToPoseDetail: (String) -> Unit,
    onStartCustomFlow: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (selectedTab) {
        Screen.Home.route -> HomeScreen(
            viewModel = viewModel,
            onNavigateToPlan = onNavigateToPlan,
            onNavigateToPlans = { onSelectTab(Screen.Plans.route) },
            onNavigateToPoses = { onSelectTab(Screen.Poses.route) },
            onNavigateToCustom = { onSelectTab(Screen.Favorites.route) },
            onNavigateToProgress = { onSelectTab(Screen.Progress.route) },
            onNavigateToProfile = onNavigateToProfile,
            onStartPracticePlan = onStartPracticePlan,
            modifier = modifier
        )
        Screen.Plans.route -> PlansScreen(
            viewModel = viewModel,
            onNavigateToPlanDetail = onNavigateToPlan,
            onStartPracticePlan = onStartPracticePlan,
            modifier = modifier
        )
        Screen.Poses.route -> PosesLibraryScreen(
            viewModel = viewModel,
            onNavigateToPoseDetail = onNavigateToPoseDetail,
            modifier = modifier
        )
        Screen.Favorites.route -> FavoritesCustomPlanScreen(
            viewModel = viewModel,
            onNavigateToPoseDetail = onNavigateToPoseDetail,
            onNavigateToPosesLibrary = { onSelectTab(Screen.Poses.route) },
            onStartCustomFlow = onStartCustomFlow,
            modifier = modifier
        )
        else -> ProgressStreakScreen(
            viewModel = viewModel,
            modifier = modifier
        )
    }
}