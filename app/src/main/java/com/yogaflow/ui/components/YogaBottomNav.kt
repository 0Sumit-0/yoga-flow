package com.yogaflow.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogaflow.ui.navigation.Screen
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.SageDark
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SagePrimaryContainer

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
)

@Composable
fun YogaBottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(
            title = "Home",
            route = Screen.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            tag = "nav_home"
        ),
        BottomNavItem(
            title = "Plans",
            route = Screen.Plans.route,
            selectedIcon = Icons.Filled.Spa,
            unselectedIcon = Icons.Outlined.Spa,
            tag = "nav_plans"
        ),
        BottomNavItem(
            title = "Poses",
            route = Screen.Poses.route,
            selectedIcon = Icons.Filled.SelfImprovement,
            unselectedIcon = Icons.Outlined.SelfImprovement,
            tag = "nav_poses"
        ),
        BottomNavItem(
            title = "Custom",
            route = Screen.Favorites.route,
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            tag = "nav_favorites"
        ),
        BottomNavItem(
            title = "Progress",
            route = Screen.Progress.route,
            selectedIcon = Icons.Filled.CalendarMonth,
            unselectedIcon = Icons.Outlined.CalendarMonth,
            tag = "nav_progress"
        )
    )

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        onNavigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                            letterSpacing = 0.8.sp
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SagePrimary,
                    selectedTextColor = SageDark,
                    indicatorColor = SagePrimaryContainer.copy(alpha = 0.7f),
                    unselectedIconColor = CharcoalMuted,
                    unselectedTextColor = CharcoalMuted
                ),
                modifier = Modifier.testTag(item.tag)
            )
        }
    }
}
