package com.yogaflow.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Spa
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.ui.components.PlanCard
import com.yogaflow.ui.components.YogaTopBar
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.viewmodel.YogaViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlansScreen(
    viewModel: YogaViewModel,
    onNavigateToPlanDetail: (String) -> Unit,
    onStartPracticePlan: (TargetPlan) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTargetFilter by remember { mutableStateOf("All") }
    val plans = viewModel.plans

    val targetCategories = listOf(
        "All",
        "Digestion",
        "Mental Focus",
        "Sleep",
        "Energy",
        "Stress Relief",
        "Flexibility"
    )

    val filteredPlans = if (selectedTargetFilter == "All") {
        plans
    } else {
        plans.filter { it.target.equals(selectedTargetFilter, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("plans_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            YogaTopBar(
                title = "Target Plans",
                subtitle = "Curated sequences for specific intentions"
            )
        }

        // Filter Pills Row
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(targetCategories) { targetName ->
                        val isSelected = selectedTargetFilter == targetName
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) SagePrimary else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (isSelected) SagePrimary else BorderSubtle),
                            modifier = Modifier
                                .clickable { selectedTargetFilter = targetName }
                                .testTag("plan_filter_$targetName")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (targetName != "All") {
                                    Icon(
                                        imageVector = Icons.Outlined.Spa,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else SagePrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = targetName,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (isSelected) Color.White else CharcoalDark,
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Header info
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredPlans.size} Curated ${if (filteredPlans.size == 1) "Sequence" else "Sequences"}",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CharcoalMuted
                    )
                )
            }
        }

        // List of Target Plans
        items(filteredPlans) { plan ->
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                PlanCard(
                    plan = plan,
                    onPlanClick = { onNavigateToPlanDetail(plan.target) },
                    onStartPractice = { onStartPracticePlan(plan) }
                )
            }
        }
    }
}
