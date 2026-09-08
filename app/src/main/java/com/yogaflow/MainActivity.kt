package com.yogaflow
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.yogaflow.ui.screens.MainTabHost
import com.yogaflow.ui.screens.OnboardingScreen
import com.yogaflow.ui.screens.PlanDetailScreen
import com.yogaflow.ui.screens.PoseDetailScreen
import com.yogaflow.ui.screens.PracticePlayerScreen
import com.yogaflow.ui.screens.ProfileScreen
import com.yogaflow.ui.screens.SplashScreen
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

//        Coil.setImageLoader(
//            ImageLoader.Builder(applicationContext)
//                .components { add(SvgDecoder.Factory()) }
//                .memoryCache {
//                    MemoryCache.Builder(applicationContext)
//                        .maxSizePercent(0.25)
//                        .build()
//                }
//                .crossfade(false)
//                .build()
//        )

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
    var selectedTab by rememberSaveable { mutableStateOf(Screen.Home.route) }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            YogaFlowBottomBar(
                navController = navController,
                selectedTab = selectedTab,
                onSelectTab = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {

            // 1. ADD THIS NEW SPLASH ROUTER
            composable("splash") {
                val profiles by viewModel.allProfiles.collectAsStateWithLifecycle()

                // 1. Draw the clean UI from your new file
                SplashScreen()

                // 2. Handle the routing logic
                LaunchedEffect(profiles) {
                    profiles?.let { loadedProfiles ->
                        if (loadedProfiles.isNotEmpty()) {
                            val existingUser = loadedProfiles.first()
                            viewModel.switchUser(existingUser.id)
                            navController.navigate(Screen.Main.route) {
                                popUpTo("splash") { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.Onboarding.route) {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }
                }
            }
            composable(Screen.Main.route) {
                MainTabHost(
                    selectedTab = selectedTab,
                    viewModel = viewModel,
                    onSelectTab = { selectedTab = it },
                    onNavigateToPlan = { target ->
                        navController.navigate(Screen.PlanDetail.createRoute(target))
                    },
                    onNavigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    },
                    onStartPracticePlan = { plan ->
                        viewModel.startPracticePlan(plan)
                        navController.navigate(Screen.PracticePlayer.createRoute(plan.id))
                    },
                    onNavigateToPoseDetail = { slug ->
                        navController.navigate(Screen.PoseDetail.createRoute(slug))
                    },
                    onStartCustomFlow = {
                        viewModel.startCustomFavoritesPractice()
                        navController.navigate(Screen.PracticePlayer.createRoute("custom_favorites"))
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

            // Pose Detail View
            composable(
                route = Screen.PoseDetail.route,
                arguments = listOf(navArgument("slug") { type = NavType.StringType })
            ) { backStackEntry ->
                val slug = backStackEntry.arguments?.getString("slug") ?: "mountain_pose"
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
                val profile by viewModel.userProfile.collectAsStateWithLifecycle()
                val isCreated = profile != null
                OnboardingScreen(
                    viewModel = viewModel,
                    onComplete = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    },
                    isProfileAlreadyCreated = isCreated
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

@Composable
private fun YogaFlowBottomBar(
    navController: NavHostController,
    selectedTab: String,
    onSelectTab: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomNav = currentRoute == Screen.Main.route
    if (!showBottomNav) return
    YogaBottomNav(
        currentRoute = selectedTab,
        onNavigate = onSelectTab
    )
}