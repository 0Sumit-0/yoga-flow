package com.yogaflow.ui.screens

import AssetSvgPoseArtwork
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.LinenSurface
import com.yogaflow.ui.theme.SageDark
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SageSubtle
import com.yogaflow.ui.theme.SandContainer
import com.yogaflow.ui.theme.SandTertiary
import com.yogaflow.ui.viewmodel.YogaViewModel

@Composable
fun PracticePlayerScreen(
    viewModel: YogaViewModel,
    onFinishPracticeAndExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val practiceState by viewModel.practiceState.collectAsStateWithLifecycle()
    val allPoses by viewModel.allPoses.collectAsStateWithLifecycle()

    if (practiceState == null) {
        Box(
            modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text("No active practice")
        }
        return
    }

    val state = practiceState!!
    val currentPoseItem = state.poseItems.getOrNull(state.currentPoseIndex)
    val currentPose = allPoses.firstOrNull { it.slug == currentPoseItem?.poseSlug }

    // Overall progress
    val overallProgress = if (state.poseItems.isNotEmpty()) {
        (state.currentPoseIndex + 1) / state.poseItems.size.toFloat()
    } else 1f

    // Current pose remaining countdown animation
    val poseProgress by animateFloatAsState(
        targetValue = if (state.totalPoseDuration > 0) {
            (state.totalPoseDuration - state.secondsRemaining) / state.totalPoseDuration.toFloat()
        } else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "pose_timer_progress"
    )

    if (state.isFinished) {
        // Practice Completed Celebration View
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(SageSubtle),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = SagePrimary,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Namaste",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = "Flow Complete",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = SageDark
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "You've successfully completed ${state.title}. This session has been automatically recorded to your streak calendar.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CharcoalMuted,
                    lineHeight = 22.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Metrics Recap Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${state.poseItems.size}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Poses",
                            style = MaterialTheme.typography.labelSmall.copy(color = CharcoalMuted)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val minutes = maxOf(1, (state.totalElapsedSeconds + 30) / 60)
                        Text(
                            text = "${minutes}m",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = SagePrimary
                            )
                        )
                        Text(
                            text = "Duration",
                            style = MaterialTheme.typography.labelSmall.copy(color = CharcoalMuted)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.target,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Target Focus",
                            style = MaterialTheme.typography.labelSmall.copy(color = CharcoalMuted)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.resetPracticeState()
                    onFinishPracticeAndExit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_finish_return_home"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SagePrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Return to Sanctuary",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
        return
    }

    // Active Flow Screen
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Bar: Plan Title, Exit Button, Overall Progress
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.resetPracticeState()
                        onFinishPracticeAndExit()
                    },
                    modifier = Modifier.testTag("btn_close_practice_player")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit Flow",
                        tint = CharcoalDark
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalDark
                        )
                    )
                    Text(
                        text = "Pose ${state.currentPoseIndex + 1} of ${state.poseItems.size}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CharcoalMuted
                        )
                    )
                }

                Text(
                    text = "End",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = SagePrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { viewModel.finishPractice() }
                        .testTag("btn_end_flow_early")
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { overallProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(CircleShape),
                color = SagePrimary,
                trackColor = BorderSubtle
            )
        }

        // Center: Circular Timer & Artwork
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background Track
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = SageSubtle,
                    strokeWidth = 8.dp
                )

                // Active Progress
                CircularProgressIndicator(
                    progress = { poseProgress },
                    modifier = Modifier.fillMaxSize(),
                    color = SagePrimary,
                    strokeWidth = 8.dp
                )

                // Inner Artwork & Countdown
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (currentPose != null) {
                        AssetSvgPoseArtwork(
                            iconSvgType = currentPose.slug,
                            size = 200.dp,
                            accentColor = SagePrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${state.secondsRemaining}s",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = CharcoalDark
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Pose Info
            if (currentPose != null) {
                Text(
                    text = currentPose.name,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = currentPose.sanskritName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        color = CharcoalMuted
                    ),
                    textAlign = TextAlign.Center
                )

                if (currentPoseItem?.side != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = SandContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = currentPoseItem.side,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SandTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        // Breathing Guidance Box
        if (currentPose != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SageSubtle),
                border = BorderStroke(1.dp, SagePrimary.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Air,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = currentPose.breathingCue,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CharcoalDark
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Player Controls (Prev, Play/Pause, Next)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous Pose Button
            IconButton(
                onClick = { viewModel.prevPose() },
                enabled = state.currentPoseIndex > 0,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (state.currentPoseIndex > 0) LinenSurface else Color.Transparent)
                    .testTag("btn_player_prev")
            ) {
                Icon(
                    imageVector = Icons.Filled.FastRewind,
                    contentDescription = "Previous Pose",
                    tint = if (state.currentPoseIndex > 0) CharcoalDark else CharcoalMuted.copy(alpha = 0.3f),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Play / Pause Primary FAB
            Surface(
                onClick = { viewModel.togglePlayPause() },
                shape = CircleShape,
                color = SagePrimary,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .size(68.dp)
                    .testTag("btn_player_play_pause")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (state.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (state.isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            // Next Pose Button
            IconButton(
                onClick = { viewModel.nextPose() },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(LinenSurface)
                    .testTag("btn_player_next")
            ) {
                Icon(
                    imageVector = Icons.Filled.FastForward,
                    contentDescription = "Next Pose",
                    tint = CharcoalDark,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
