package com.yogaflow.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yogaflow.data.model.UserProfile
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.SageDark
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SageSubtle
import com.yogaflow.ui.viewmodel.YogaViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    viewModel: YogaViewModel,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(1) } // 1: Name & Age, 2: Level & Gender, 3: Goals

    var name by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("28") }
    var gender by remember { mutableStateOf("Female") }
    var experienceLevel by remember { mutableStateOf("Beginner") }
    var selectedGoals by remember {
        mutableStateOf(setOf("Stress Relief", "Better Sleep", "Flexibility"))
    }

    val totalSteps = 3
    val progress = step / totalSteps.toFloat()

    val availableGoals = listOf(
        "Digestion" to "Ease bloating & support gut motility",
        "Mental Focus" to "Center mind with balance & breath",
        "Better Sleep" to "Soothe nervous system before night",
        "Energy" to "Invigorating postures for vitality",
        "Stress Relief" to "Passive holds to release tension",
        "Flexibility" to "Full-body mobility & range"
    )

    val experienceLevels = listOf(
        "Beginner" to "New to yoga or returning after a break",
        "Intermediate" to "Comfortable with basic poses & transitions",
        "Advanced" to "Deep regular practice & challenging holds"
    )

    val genders = listOf("Female", "Male", "Non-binary", "Prefer not to say")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Top Navigation & Progress
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (step > 1) {
                    IconButton(
                        onClick = { step-- },
                        modifier = Modifier.testTag("onboarding_btn_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CharcoalDark
                        )
                    }
                } else {
                    Box(modifier = Modifier.size(48.dp))
                }

                Text(
                    text = "Step $step of $totalSteps",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = CharcoalMuted,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = SagePrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .clickable {
                            val profile = UserProfile(
                                id = "user_default",
                                name = if (name.isNotBlank()) name else "Elena",
                                age = ageText.toIntOrNull() ?: 28,
                                gender = gender,
                                level = experienceLevel,
                                goals = selectedGoals.toList()
                            )
                            viewModel.saveProfile(profile)
                            onComplete()
                        }
                        .testTag("onboarding_btn_skip")
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = SagePrimary,
                trackColor = BorderSubtle
            )

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedContent(targetState = step, label = "onboarding_step") { currentStep ->
                when (currentStep) {
                    1 -> {
                        Column {
                            Surface(
                                color = SageSubtle,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Outlined.Spa,
                                        contentDescription = null,
                                        tint = SagePrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Welcome to Yoga Flow",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )

                            Text(
                                text = "Personalize your sanctuary. What should we call you during practice?",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = CharcoalMuted
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "Your Name",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = CharcoalDark
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                placeholder = { Text("e.g. Elena") },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("onboarding_input_name"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SagePrimary,
                                    unfocusedBorderColor = BorderSubtle,
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                                )
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Your Age",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = CharcoalDark
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = ageText,
                                onValueChange = { ageText = it },
                                placeholder = { Text("e.g. 28") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("onboarding_input_age"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SagePrimary,
                                    unfocusedBorderColor = BorderSubtle,
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }

                    2 -> {
                        Column {
                            Text(
                                text = "Practice Experience",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )

                            Text(
                                text = "We'll tailor pose sequence pacing and cues to your comfort level.",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = CharcoalMuted
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Experience Level",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = CharcoalDark
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            experienceLevels.forEach { (level, desc) ->
                                val isSelected = experienceLevel == level
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable { experienceLevel = level }
                                        .testTag("level_option_$level"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) SageSubtle else MaterialTheme.colorScheme.surface
                                    ),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) SagePrimary else BorderSubtle
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = level,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = if (isSelected) SageDark else CharcoalDark
                                                )
                                            )
                                            Text(
                                                text = desc,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = CharcoalMuted
                                                )
                                            )
                                        }
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                tint = SagePrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Gender Identity",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = CharcoalDark
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                genders.forEach { g ->
                                    val isSelected = gender == g
                                    Surface(
                                        color = if (isSelected) SagePrimary else MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, if (isSelected) SagePrimary else BorderSubtle),
                                        modifier = Modifier.clickable { gender = g }
                                    ) {
                                        Text(
                                            text = g,
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = if (isSelected) Color.White else CharcoalDark,
                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        Column {
                            Text(
                                text = "Wellness Goals",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )

                            Text(
                                text = "Select your top intentions. You can switch target flows anytime.",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = CharcoalMuted
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            availableGoals.forEach { (goalName, goalDesc) ->
                                val isSelected = selectedGoals.contains(goalName)
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable {
                                            selectedGoals = if (isSelected) {
                                                if (selectedGoals.size > 1) selectedGoals - goalName else selectedGoals
                                            } else {
                                                selectedGoals + goalName
                                            }
                                        }
                                        .testTag("goal_option_$goalName"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) SageSubtle else MaterialTheme.colorScheme.surface
                                    ),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) SagePrimary else BorderSubtle
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = goalName,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = if (isSelected) SageDark else CharcoalDark
                                                )
                                            )
                                            Text(
                                                text = goalDesc,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = CharcoalMuted
                                                )
                                            )
                                        }
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                tint = SagePrimary,
                                                modifier = Modifier.size(20.dp)
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

        // Bottom Continue / Finish Button
        Column(modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
            Button(
                onClick = {
                    if (step < 3) {
                        step++
                    } else {
                        val profile = UserProfile(
                            id = "user_default",
                            name = if (name.isNotBlank()) name else "Practitioner",
                            age = ageText.toIntOrNull() ?: 28,
                            gender = gender,
                            level = experienceLevel,
                            goals = selectedGoals.toList()
                        )
                        viewModel.saveProfile(profile)
                        onComplete()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("onboarding_btn_next"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SagePrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (step == 3) "Complete & Enter Studio" else "Continue",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
