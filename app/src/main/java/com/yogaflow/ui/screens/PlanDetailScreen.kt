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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.ui.components.CalmPoseArtwork
import com.yogaflow.ui.components.YogaTopBar
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.LinenSurface
import com.yogaflow.ui.theme.SageDark
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SageSubtle
import com.yogaflow.ui.theme.SandTertiary
import com.yogaflow.ui.viewmodel.YogaViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlanDetailScreen(
    targetOrId: String,
    viewModel: YogaViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPoseDetail: (String) -> Unit,
    onStartPracticePlan: (TargetPlan) -> Unit,
    modifier: Modifier = Modifier
) {
    val allPoses by viewModel.allPoses.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val favSlugs = favorites.map { it.poseSlug }.toSet()

    val plan = viewModel.plans.firstOrNull {
        it.target.equals(targetOrId, ignoreCase = true) || it.id.equals(targetOrId, ignoreCase = true)
    } ?: viewModel.plans.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("plan_detail_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            YogaTopBar(
                title = plan.target,
                onBackClick = onNavigateBack
            )
        }

        // Plan Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SageSubtle),
                border = BorderStroke(1.dp, SagePrimary.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = SagePrimary,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = plan.level,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = CharcoalDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${plan.estimatedMinutes} Minutes",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = CharcoalDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = plan.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalDark
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = plan.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CharcoalMuted,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Highlights
                    Text(
                        text = "Target Benefits:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalDark
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    plan.highlights.forEach { highlight ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = highlight,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = CharcoalDark
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { onStartPracticePlan(plan) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_begin_plan_flow"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SagePrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Begin Guided Flow (${plan.estimatedMinutes} min)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }

        // Sequence Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pose Sequence (${plan.poses.size} Poses)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }

        // List of Poses in this Sequence
        itemsIndexed(plan.poses) { index, itemPose ->
            val pose = allPoses.firstOrNull { it.slug == itemPose.poseSlug }
            if (pose != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onNavigateToPoseDetail(pose.slug) }
                        .testTag("plan_pose_item_${pose.slug}"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, BorderSubtle)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Sequence Number Badge
                        Surface(
                            shape = CircleShape,
                            color = LinenSurface,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${index + 1}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SageDark
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        CalmPoseArtwork(
                            iconType = pose.iconType,
                            size = 54.dp,
                            accentColor = SagePrimary
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = pose.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = CharcoalDark
                                )
                            )
                            Text(
                                text = pose.sanskritName,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic,
                                    color = CharcoalMuted
                                )
                            )
                            if (itemPose.side != null) {
                                Text(
                                    text = itemPose.side,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SandTertiary,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }

                        Surface(
                            color = SageSubtle,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${itemPose.durationSeconds}s",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SagePrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
