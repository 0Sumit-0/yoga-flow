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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yogaflow.ui.components.PoseCard
import com.yogaflow.ui.components.YogaTopBar
import com.yogaflow.ui.theme.BorderSubtle
import com.yogaflow.ui.theme.CharcoalDark
import com.yogaflow.ui.theme.CharcoalMuted
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.viewmodel.YogaViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PosesLibraryScreen(
    viewModel: YogaViewModel,
    onNavigateToPoseDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredPoses by viewModel.filteredPoses.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedTargetFilter by viewModel.selectedTargetFilter.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val favSlugs = favorites.map { it.poseSlug }.toSet()

    val difficulties = listOf("All", "Beginner", "Intermediate", "Advanced")
    val targets = listOf("All", "Digestion", "Mental Focus", "Sleep", "Energy", "Stress Relief", "Flexibility")
    val categories = listOf("All", "Seated", "Standing", "Backbend", "Inversion", "Restorative", "Forward Bend", "Balancing", "Twist")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("poses_library_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            YogaTopBar(
                title = "Pose Library",
                subtitle = "Explore asanas, alignments & benefits"
            )
        }

        // Search Bar
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = { Text("Search by name, Sanskrit, or target...", style = MaterialTheme.typography.bodyMedium) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = CharcoalMuted
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear search",
                                    tint = CharcoalMuted
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_search_poses"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SagePrimary,
                        unfocusedBorderColor = BorderSubtle,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        // Target Filter Chips
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Text(
                    text = "Target Focus",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = CharcoalDark
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(targets) { target ->
                        val isSelected = selectedTargetFilter == target
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) SagePrimary else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (isSelected) SagePrimary else BorderSubtle),
                            modifier = Modifier
                                .clickable { viewModel.setTargetFilter(target) }
                                .testTag("filter_target_$target")
                        ) {
                            Text(
                                text = target,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isSelected) Color.White else CharcoalDark,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        }

        // Difficulty Chips
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                Text(
                    text = "Difficulty Level",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = CharcoalDark
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(difficulties) { diff ->
                        val isSelected = selectedDifficulty == diff
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) SagePrimary else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (isSelected) SagePrimary else BorderSubtle),
                            modifier = Modifier
                                .clickable { viewModel.setDifficultyFilter(diff) }
                                .testTag("filter_diff_$diff")
                        ) {
                            Text(
                                text = diff,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isSelected) Color.White else CharcoalDark,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        }

        // Category Chips
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                Text(
                    text = "Pose Category",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = CharcoalDark
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) SagePrimary else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (isSelected) SagePrimary else BorderSubtle),
                            modifier = Modifier
                                .clickable { viewModel.setCategoryFilter(cat) }
                                .testTag("filter_cat_$cat")
                        ) {
                            Text(
                                text = cat,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isSelected) Color.White else CharcoalDark,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        }

        // Results Count Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredPoses.size} ${if (filteredPoses.size == 1) "Pose" else "Poses"}",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CharcoalMuted
                    )
                )
            }
        }

        // Pose Cards
        if (filteredPoses.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, BorderSubtle)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Spa,
                            contentDescription = null,
                            tint = CharcoalMuted,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No poses found",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Try clearing your filters or search terms.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalMuted
                            )
                        )
                    }
                }
            }
        } else {
            items(filteredPoses) { pose ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
                    PoseCard(
                        pose = pose,
                        isFavorite = pose.slug in favSlugs,
                        onPoseClick = { onNavigateToPoseDetail(pose.slug) },
                        onToggleFavorite = { viewModel.toggleFavorite(pose.slug) }
                    )
                }
            }
        }
    }
}
