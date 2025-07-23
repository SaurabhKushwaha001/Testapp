package com.example.testapp.screens
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import coil.compose.rememberAsyncImagePainter
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.viewmodel.CourseDetailViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    navController: NavController,
    playlistId: String?,
    viewModel: CourseDetailViewModel = viewModel()
) {
    val videos by viewModel.videos.collectAsState()
    val watchedVideos by viewModel.watchedVideos.collectAsState()

    LaunchedEffect(playlistId) {
        playlistId?.let { viewModel.fetchVideos(it) }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    "Course Videos",
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("CourseHomeScreen") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            modifier = Modifier.shadow(8.dp)
        )
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(innerPadding)
        ) {
            items(videos) { video ->
                val isWatched = watchedVideos.contains(video.contentDetails.videoId)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("playVideo/${playlistId}/${video.contentDetails.videoId}")
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isWatched) Color(0xFFE8F5E8) else Color(0xFFF5F5F5)
                    )
                ) {
                    Box {
                        Row(modifier = Modifier.padding(12.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(video.snippet.thumbnails.medium.url),
                                contentDescription = "Video Thumbnail",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = video.snippet.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 60.dp) // Make space for the status text
                            )
                        }

                        // Status indicator in top-right corner
                        Text(
                            text = if (isWatched) "Watched" else "Unwatched",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = if (isWatched) Color(0xFF4CAF50) else Color.Gray,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}