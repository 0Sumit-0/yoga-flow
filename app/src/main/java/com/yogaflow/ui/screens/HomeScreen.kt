package com.yogaflow.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.data.model.mapGoalToPlanTarget
import com.yogaflow.ui.components.PlanCard
import com.yogaflow.ui.components.WeeklyStreakCard
import com.yogaflow.ui.theme.BorderAccent
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.LinenSurface
import com.yogaflow.ui.theme.NaturalLightTint
import com.yogaflow.ui.theme.NaturalOliveAccent
import com.yogaflow.ui.theme.NaturalOliveDark
import com.yogaflow.ui.theme.NaturalOlivePrimary
import com.yogaflow.ui.theme.NaturalTaupeMuted
import com.yogaflow.ui.theme.PureWhite
import com.yogaflow.ui.theme.SoftHeartPink
import com.yogaflow.ui.theme.WarmIvory
import com.yogaflow.ui.viewmodel.YogaViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    viewModel: YogaViewModel,
    onNavigateToPlan: (String) -> Unit,
    onNavigateToPlans: () -> Unit,
    onNavigateToPoses: () -> Unit,
    onNavigateToCustom: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onStartPracticePlan: (TargetPlan) -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val streakStats by viewModel.streakStats.collectAsStateWithLifecycle()
    val favoritePoses by viewModel.favoritePoses.collectAsStateWithLifecycle()
    val completions by viewModel.completions.collectAsStateWithLifecycle()
    val plans = viewModel.plans

    // Determine primary recommended plan based on selected or default goal
    var selectedTarget by remember { mutableStateOf<String?>(null) }
    val primaryGoal = mapGoalToPlanTarget(selectedTarget ?: profile?.goals?.firstOrNull() ?: "Stress Relief")
    val recommendedPlan = plans.firstOrNull { it.target.equals(primaryGoal, ignoreCase = true) }
        ?: plans.first()

    // Formatted current date e.g. "Wednesday, May 24"
    val formattedDate = remember {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        dateFormat.format(Date()).uppercase()
    }

    val practitionerName = profile?.name ?: "Maya"
    val initialLetter = practitionerName.firstOrNull()?.uppercaseChar()?.toString() ?: "M"

    val targetsList = listOf("Mental Focus", "Deep Sleep", "Energy", "Stress Relief", "Digestion", "Flexibility")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Natural Tones Header: Date, Namaste Greeting & Avatar with Streak Badge
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = NaturalOliveDark,
                            letterSpacing = 1.5.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Namaste, $practitionerName",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = NaturalOliveDark,
                            fontSize = 26.sp
                        )
                    )
                }

                // Avatar with streak count badge (tappable to view profile)
                Box(
                    modifier = Modifier
                        .clickable(onClick = onNavigateToProfile)
                        .testTag("btn_home_profile"),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(NaturalOliveAccent)
                            .border(2.dp, PureWhite, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initialLetter,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = NaturalOliveDark
                            )
                        )
                    }

                    // Streak mini-pill
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(NaturalOlivePrimary)
                            .border(1.5.dp, WarmIvory, CircleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${streakStats.currentStreak}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
        }

        // Section: "Your Target for Today"
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "YOUR TARGET FOR TODAY",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = NaturalOliveDark,
                        letterSpacing = 1.2.sp
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(targetsList) { target ->
                        val isSelected = mapGoalToPlanTarget(target) == primaryGoal

                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = if (isSelected) NaturalOlivePrimary else PureWhite,
                            border = if (isSelected) null else BorderStroke(1.dp, BorderAccent),
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier
                                .clickable {
                                    selectedTarget = mapGoalToPlanTarget(target)
                                }
                                .testTag("target_tab_$target")
                        ) {
                            Text(
                                text = target,
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = if (isSelected) PureWhite else NaturalOliveDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Section: Recommended Plan Hero Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                PlanCard(
                    plan = recommendedPlan,
                    isFeatured = true,
                    onPlanClick = { onNavigateToPlan(recommendedPlan.target) },
                    onStartPractice = { onStartPracticePlan(recommendedPlan) }
                )
            }
        }

        // Section: Weekly Streak Card (7-day visual dots matching Natural Tones design)
        item {
            WeeklyStreakCard(
                currentStreak = streakStats.currentStreak,
                completedDates = streakStats.completedDatesSet,
                onClick = onNavigateToProgress,
                modifier = Modifier.testTag("home_streak_banner")
            )
        }

        // Target Categories Quick Access
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Practice by Target Goal",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = NaturalOliveDark
                        )
                    )
                    Text(
                        text = "See All (${plans.size})",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = NaturalOlivePrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .clickable(onClick = onNavigateToPlans)
                            .testTag("btn_see_all_plans")
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(plans) { plan ->
                        Card(
                            modifier = Modifier
                                .width(180.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onNavigateToPlan(plan.target) }
                                .testTag("target_chip_${plan.id}"),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, BorderSubtle),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Surface(
                                    color = NaturalLightTint,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.Spa,
                                            contentDescription = null,
                                            tint = NaturalOlivePrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = plan.target,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = NaturalOliveDark
                                    )
                                )
                                Text(
                                    text = "${plan.estimatedMinutes}m • ${plan.poses.size} poses",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = NaturalTaupeMuted
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Personal Custom Flow from Favourites Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .clickable(onClick = onNavigateToCustom)
                        .testTag("home_custom_flow_card"),
                    colors = CardDefaults.cardColors(
                        containerColor = LinenSurface
                    ),
                    border = BorderStroke(1.dp, BorderSubtle),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(PureWhite),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = null,
                                    tint = SoftHeartPink,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Personal Custom Flow",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = NaturalOliveDark
                                    )
                                )
                                Text(
                                    text = if (favoritePoses.isNotEmpty())
                                        "${favoritePoses.size} hearted poses ready"
                                    else
                                        "Heart poses from library to create flow",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = NaturalTaupeMuted
                                    )
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Go to custom flow",
                            tint = NaturalTaupeMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Recent Completions History
        if (completions.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Practices",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "Streak Log",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = NaturalOlivePrimary,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.clickable(onClick = onNavigateToProgress)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    completions.take(3).forEach { completion ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, BorderSubtle)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.CheckCircle,
                                        contentDescription = null,
                                        tint = NaturalOlivePrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = completion.planTarget,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = NaturalOliveDark
                                            )
                                        )
                                        Text(
                                            text = completion.completedDate,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = NaturalTaupeMuted
                                            )
                                        )
                                    }
                                }

                                Surface(
                                    color = NaturalLightTint,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "${completion.durationMinutes} min",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = NaturalOlivePrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

