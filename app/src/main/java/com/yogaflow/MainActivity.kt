package com.yogaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yogaflow.data.db.YogaDatabase
import com.yogaflow.data.repository.YogaRepository
import com.yogaflow.ui.components.YogaBottomNav
import com.yogaflow.ui.navigation.Screen
import com.yogaflow.ui.screens.AuthScreen
import com.yogaflow.ui.screens.FavoritesCustomPlanScreen
import com.yogaflow.ui.screens.HomeScreen
import com.yogaflow.ui.screens.OnboardingScreen
import com.yogaflow.ui.screens.PlanDetailScreen
import com.yogaflow.ui.screens.PlansScreen
import com.yogaflow.ui.screens.PoseDetailScreen
import com.yogaflow.ui.screens.PosesLibraryScreen
import com.yogaflow.ui.screens.PracticePlayerScreen
import com.yogaflow.ui.screens.ProfileScreen
import com.yogaflow.ui.screens.ProgressStreakScreen
import com.yogaflow.ui.theme.MyApplicationTheme
import com.yogaflow.ui.viewmodel.YogaViewModel
import com.yogaflow.ui.viewmodel.YogaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = YogaDatabase.getDatabase(this, lifecycleScope)
        val repository = YogaRepository(database.yogaDao())
        val factory = YogaViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                val viewModel: YogaViewModel = viewModel(factory = factory)
                YogaFlowApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun YogaFlowApp(
    viewModel: YogaViewModel,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Top-level destinations that show the bottom navigation bar
    val bottomNavRoutes = setOf(
        Screen.Home.route,
        Screen.Plans.route,
        Screen.Poses.route,
        Screen.Favorites.route,
        Screen.Progress.route
    )

    val showBottomNav = currentRoute in bottomNavRoutes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                YogaBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home / Dashboard
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToPlan = { target ->
                        navController.navigate(Screen.PlanDetail.createRoute(target))
                    },
                    onNavigateToPlans = {
                        navController.navigate(Screen.Plans.route)
                    },
                    onNavigateToPoses = {
                        navController.navigate(Screen.Poses.route)
                    },
                    onNavigateToCustom = {
                        navController.navigate(Screen.Favorites.route)
                    },
                    onNavigateToProgress = {
                        navController.navigate(Screen.Progress.route)
                    },
                    onNavigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    },
                    onStartPracticePlan = { plan ->
                        viewModel.startPracticePlan(plan)
                        navController.navigate(Screen.PracticePlayer.createRoute(plan.id))
                    }
                )
            }

            // Target-Based Plans List
            composable(Screen.Plans.route) {
                PlansScreen(
                    viewModel = viewModel,
                    onNavigateToPlanDetail = { target ->
                        navController.navigate(Screen.PlanDetail.createRoute(target))
                    },
                    onStartPracticePlan = { plan ->
                        viewModel.startPracticePlan(plan)
                        navController.navigate(Screen.PracticePlayer.createRoute(plan.id))
                    }
                )
            }

            // Target Plan Sequence Detail
            composable(
                route = Screen.PlanDetail.route,
                arguments = listOf(navArgument("target") { type = NavType.StringType })
            ) { backStackEntry ->
                val target = backStackEntry.arguments?.getString("target") ?: "Stress Relief"
                PlanDetailScreen(
                    targetOrId = target,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToPoseDetail = { slug ->
                        navController.navigate(Screen.PoseDetail.createRoute(slug))
                    },
                    onStartPracticePlan = { plan ->
                        viewModel.startPracticePlan(plan)
                        navController.navigate(Screen.PracticePlayer.createRoute(plan.id))
                    }
                )
            }

            // Pose Library
            composable(Screen.Poses.route) {
                PosesLibraryScreen(
                    viewModel = viewModel,
                    onNavigateToPoseDetail = { slug ->
                        navController.navigate(Screen.PoseDetail.createRoute(slug))
                    }
                )
            }

            // Pose Detail View
            composable(
                route = Screen.PoseDetail.route,
                arguments = listOf(navArgument("slug") { type = NavType.StringType })
            ) { backStackEntry ->
                val slug = backStackEntry.arguments?.getString("slug") ?: "childs-pose"
                PoseDetailScreen(
                    slug = slug,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onStartSinglePosePractice = { pose ->
                        viewModel.startSinglePosePractice(pose)
                        navController.navigate(Screen.PracticePlayer.createRoute("single_${pose.slug}"))
                    }
                )
            }

            // Favorites & Custom Sequence
            composable(Screen.Favorites.route) {
                FavoritesCustomPlanScreen(
                    viewModel = viewModel,
                    onNavigateToPoseDetail = { slug ->
                        navController.navigate(Screen.PoseDetail.createRoute(slug))
                    },
                    onNavigateToPosesLibrary = {
                        navController.navigate(Screen.Poses.route)
                    },
                    onStartCustomFlow = {
                        viewModel.startCustomFavoritesPractice()
                        navController.navigate(Screen.PracticePlayer.createRoute("custom_favorites"))
                    }
                )
            }

            // Streak Calendar & Progress
            composable(Screen.Progress.route) {
                ProgressStreakScreen(
                    viewModel = viewModel
                )
            }

            // Profile & Settings
            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToAuth = {
                        navController.navigate(Screen.Auth.route)
                    },
                    onRestartOnboarding = {
                        navController.navigate(Screen.Onboarding.route)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Onboarding Flow
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    viewModel = viewModel,
                    onComplete = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            // Auth / Switch Account
            composable(Screen.Auth.route) {
                AuthScreen(
                    viewModel = viewModel,
                    onAuthSuccess = {
                        navController.popBackStack()
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // Interactive Guided Flow Player
            composable(
                route = Screen.PracticePlayer.route,
                arguments = listOf(navArgument("planId") { type = NavType.StringType })
            ) {
                PracticePlayerScreen(
                    viewModel = viewModel,
                    onFinishPracticeAndExit = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
