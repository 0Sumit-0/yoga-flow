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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.repository.StreakStats
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalLight
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.LinenSurface
import com.yogaflow.ui.theme.SageDark
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SageSubtle
import com.yogaflow.ui.theme.SandTertiary
import com.yogaflow.ui.theme.SoftGoldContainer
import com.yogaflow.ui.theme.SoftGoldStreak
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class CalendarDay(
    val dayNumber: Int,
    val dateString: String,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val isCompleted: Boolean
)

@Composable
fun StreakCalendarView(
    streakStats: StreakStats,
    completions: List<PracticeCompletion>,
    onDaySelected: (String, List<PracticeCompletion>) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var calendarMonthOffset by remember { mutableStateOf(0) }
    val currentCal = remember(calendarMonthOffset) {
        Calendar.getInstance().apply {
            add(Calendar.MONTH, calendarMonthOffset)
        }
    }

    val monthYearFormat = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()) }
    val monthTitle = remember(currentCal) { monthYearFormat.format(currentCal.time) }

    val days = remember(currentCal, streakStats.completedDatesSet) {
        generateCalendarDays(currentCal, streakStats.completedDatesSet)
    }

    val todayStr = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
    var selectedDateStr by remember { mutableStateOf(todayStr) }

    Column(modifier = modifier.fillMaxWidth()) {
        // Streak Summary Banner Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Current Streak Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .testTag("streak_card_current"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SoftGoldContainer),
                border = BorderStroke(1.dp, SoftGoldStreak.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SoftGoldStreak.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = null,
                            tint = SoftGoldStreak,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${streakStats.currentStreak} Days",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Current Streak",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CharcoalMuted
                            )
                        )
                    }
                }
            }

            // Total Sessions Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .testTag("streak_card_total"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SageSubtle),
                border = BorderStroke(1.dp, SagePrimary.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SagePrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Spa,
                            contentDescription = null,
                            tint = SagePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${streakStats.totalPractices} Flows",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Total Sessions",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CharcoalMuted
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Secondary Metrics Row (Best streak & Total minutes)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEvents,
                        contentDescription = null,
                        tint = SandTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Best Streak: ${streakStats.bestStreak} days",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = CharcoalDark,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Time on Mat: ${streakStats.totalMinutes}m",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = CharcoalDark,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Calendar Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, BorderSubtle)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Month Header & Navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { calendarMonthOffset-- },
                        modifier = Modifier.testTag("btn_prev_month")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous Month",
                            tint = CharcoalDark
                        )
                    }

                    Text(
                        text = monthTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalDark
                        )
                    )

                    IconButton(
                        onClick = { calendarMonthOffset++ },
                        modifier = Modifier.testTag("btn_next_month")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next Month",
                            tint = CharcoalDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Days of week header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                    daysOfWeek.forEach { dayName ->
                        Text(
                            text = dayName,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CharcoalMuted,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Days Grid (6 rows x 7 cols)
                val rows = days.chunked(7)
                rows.forEach { rowDays ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowDays.forEach { day ->
                            val isSelected = day.dateString == selectedDateStr
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(3.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        when {
                                            day.isCompleted -> SagePrimary
                                            isSelected -> SageSubtle
                                            day.isToday -> LinenSurface
                                            else -> Color.Transparent
                                        }
                                    )
                                    .border(
                                        width = if (day.isToday && !day.isCompleted) 1.5.dp else 0.dp,
                                        color = if (day.isToday && !day.isCompleted) SagePrimary else Color.Transparent,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable(enabled = day.isCurrentMonth) {
                                        selectedDateStr = day.dateString
                                        val dayCompletions = completions.filter { it.completedDate == day.dateString }
                                        onDaySelected(day.dateString, dayCompletions)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = if (day.dayNumber > 0) "${day.dayNumber}" else "",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (day.isCompleted || day.isToday) FontWeight.Bold else FontWeight.Normal,
                                            color = when {
                                                !day.isCurrentMonth -> CharcoalLight.copy(alpha = 0.4f)
                                                day.isCompleted -> Color.White
                                                day.isToday -> SagePrimary
                                                isSelected -> SageDark
                                                else -> CharcoalDark
                                            }
                                        )
                                    )

                                    if (day.isCompleted) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.8f),
                                            modifier = Modifier.size(10.dp)
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
}

private fun generateCalendarDays(calendar: Calendar, completedDates: Set<String>): List<CalendarDay> {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayStr = sdf.format(Date())

    val cal = calendar.clone() as Calendar
    cal.set(Calendar.DAY_OF_MONTH, 1)

    // Java Calendar: Sunday = 1, Monday = 2, ... Saturday = 7
    // Adjust to Monday = 0
    val firstDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val daysList = mutableListOf<CalendarDay>()

    // Leading days from previous month
    val prevMonthCal = cal.clone() as Calendar
    prevMonthCal.add(Calendar.MONTH, -1)
    val maxDaysPrevMonth = prevMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)

    for (i in 0 until firstDayOfWeek) {
        val dayNum = maxDaysPrevMonth - firstDayOfWeek + i + 1
        prevMonthCal.set(Calendar.DAY_OF_MONTH, dayNum)
        val dateStr = sdf.format(prevMonthCal.time)
        daysList.add(
            CalendarDay(
                dayNumber = dayNum,
                dateString = dateStr,
                isCurrentMonth = false,
                isToday = dateStr == todayStr,
                isCompleted = completedDates.contains(dateStr)
            )
        )
    }

    // Days in current month
    for (day in 1..maxDaysInMonth) {
        cal.set(Calendar.DAY_OF_MONTH, day)
        val dateStr = sdf.format(cal.time)
        daysList.add(
            CalendarDay(
                dayNumber = day,
                dateString = dateStr,
                isCurrentMonth = true,
                isToday = dateStr == todayStr,
                isCompleted = completedDates.contains(dateStr)
            )
        )
    }

    // Trailing days to fill 35 or 42 grid slots
    val nextMonthCal = cal.clone() as Calendar
    nextMonthCal.add(Calendar.MONTH, 1)
    var nextMonthDay = 1
    while (daysList.size % 7 != 0 || daysList.size < 35) {
        nextMonthCal.set(Calendar.DAY_OF_MONTH, nextMonthDay)
        val dateStr = sdf.format(nextMonthCal.time)
        daysList.add(
            CalendarDay(
                dayNumber = nextMonthDay,
                dateString = dateStr,
                isCurrentMonth = false,
                isToday = dateStr == todayStr,
                isCompleted = completedDates.contains(dateStr)
            )
        )
        nextMonthDay++
    }

    return daysList
}
