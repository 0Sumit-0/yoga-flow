package com.yogaflow.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Spa
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogaflow.data.model.TargetPlan
import com.yogaflow.ui.theme.BorderAccent
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.NaturalOliveAccent
import com.yogaflow.ui.theme.NaturalOliveDark
import com.yogaflow.ui.theme.PureWhite
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SageSubtle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlanCard(
    plan: TargetPlan,
    onPlanClick: () -> Unit,
    onStartPractice: () -> Unit,
    modifier: Modifier = Modifier,
    isFeatured: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onPlanClick)
            .testTag("plan_card_${plan.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isFeatured) NaturalOliveAccent else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, if (isFeatured) BorderAccent else BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isFeatured) 0.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            // Target header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Goal Target Pill
                Surface(
                    color = if (isFeatured) PureWhite.copy(alpha = 0.6f) else SagePrimary,
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Spa,
                            contentDescription = null,
                            tint = if (isFeatured) NaturalOliveDark else PureWhite,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isFeatured) "RECOMMENDED PLAN" else plan.target.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isFeatured) NaturalOliveDark else PureWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                // Duration & Level Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = null,
                        tint = if (isFeatured) NaturalOliveDark else CharcoalDark,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${plan.estimatedMinutes}m",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if (isFeatured) NaturalOliveDark else CharcoalDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "•  ${plan.poses.size} Poses",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isFeatured) NaturalOliveDark else CharcoalDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Plan Title & Subtitle
            Text(
                text = plan.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isFeatured) NaturalOliveDark else MaterialTheme.colorScheme.onSurface,
                    lineHeight = 28.sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = plan.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isFeatured) NaturalOliveDark else CharcoalMuted,
                    lineHeight = 20.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Highlights
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                plan.highlights.take(2).forEach { highlight ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                color = if (isFeatured) PureWhite.copy(alpha = 0.5f) else SageSubtle,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = if (isFeatured) NaturalOliveDark else SagePrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = highlight,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isFeatured) NaturalOliveDark else CharcoalDark,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View Sequence",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = if (isFeatured) NaturalOliveDark else SagePrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable(onClick = onPlanClick)
                )

                Button(
                    onClick = onStartPractice,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SagePrimary,
                        contentColor = PureWhite
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("start_plan_${plan.id}")
                ) {
                    Text(
                        text = "Begin Flow",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}
