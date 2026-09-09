package com.yogaflow.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.NaturalLightTint
import com.yogaflow.ui.theme.NaturalOliveAccent
import com.yogaflow.ui.theme.NaturalOliveDark
import com.yogaflow.ui.theme.NaturalOlivePrimary
import com.yogaflow.ui.theme.PureWhite
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WeeklyStreakCard(
    currentStreak: Int,
    completedDates: Set<String>, // Passed as String formatted "yyyy-MM-DD" from Repository
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dayNameFormat = SimpleDateFormat("EEEEE", Locale.getDefault()) // Single letter day (e.g. M, T, W)

    val todayCal = Calendar.getInstance()
    val todayStr = sdf.format(todayCal.time)

    // Find Monday of the current week
    val mondayCal = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    }

    // Create Monday -> Sunday list
    val weekDays = (0..6).map { index ->
        (mondayCal.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, index)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = PureWhite
        ),
        border = BorderStroke(
            1.dp,
            BorderSubtle
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Weekly Streak",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = NaturalOliveDark
                    )
                )

                Surface(
                    color = NaturalLightTint,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "$currentStreak Day Streak",
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 4.dp
                        ),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NaturalOlivePrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // Monday -> Sunday
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                weekDays.forEach { dayCal ->
                    val dateStr = sdf.format(dayCal.time)
                    val isToday = dateStr == todayStr
                    val isCompleted = dateStr in completedDates
                    val dayLetter = dayNameFormat.format(dayCal.time)
                    val dayOfMonth = dayCal.get(Calendar.DAY_OF_MONTH).toString()

                    WeeklyStreakDay(
                        dayLetter = dayLetter,
                        dayOfMonth = dayOfMonth,
                        isToday = isToday,
                        isCompleted = isCompleted
                    )
                }
            }
        }
    }
}

@Composable
private fun WeeklyStreakDay(
    dayLetter: String,
    dayOfMonth: String,
    isToday: Boolean,
    isCompleted: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = dayLetter,
            style = MaterialTheme.typography.labelSmall.copy(
                color = NaturalOliveDark,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        )

        when {
            // Today + completed
            isToday && isCompleted -> {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(
                            NaturalOliveAccent.copy(
                                alpha = 0.4f
                            )
                        )
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CompletedCircle()
                }
            }

            // Completed day
            isCompleted -> {
                CompletedCircle()
            }

            // Not completed
            else -> {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(NaturalLightTint)
                        .border(
                            1.dp,
                            BorderSubtle,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayOfMonth,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NaturalOliveDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun CompletedCircle() {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(NaturalOlivePrimary),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Completed",
            tint = PureWhite,
            modifier = Modifier.size(16.dp)
        )
    }
}