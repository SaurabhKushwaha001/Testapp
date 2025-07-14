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

    LaunchedEffect(playlistId) {
        playlistId?.let { viewModel.fetchVideos(it) }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Course Videos",
        modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center) },
        navigationIcon = {
        IconButton(onClick = {
            navController.navigate("CourseHomeScreen"){
                popUpTo(navController.graph.startDestinationId) { // Pop up to the start destination of the graph
                    inclusive = true // Also remove the start destination itself from the back stack
                }
                launchSingleTop = true // Avoid multiple copies of the home screen on top of each other
            }
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use AutoMirrored for RTL support
                contentDescription = "Back"
            )
        }
    }) }) {innerPadding->
        LazyColumn(modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(innerPadding)
        ) {
            items(videos) { video ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("playVideo/${video.contentDetails.videoId}")
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
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
                        )
                    }
                }
            }
        }
    }
}
