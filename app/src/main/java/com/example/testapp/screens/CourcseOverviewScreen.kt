package com.example.testapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.viewmodel.CourseProgressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseOverviewScreen(
    navController: NavController,
    playlistId: String,
    thumbnailUrl: String,
    title: String,
    description: String,
    viewModel: CourseProgressViewModel = viewModel()
) {
    val context = LocalContext.current

    Scaffold(topBar = { TopAppBar(title = { Text("Course Videos",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center) },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close"
                )
            }
        }) }){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(thumbnailUrl),
                contentDescription = "Course Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.startCourse(playlistId, title, thumbnailUrl)
                    navController.navigate("CourseDetail/$playlistId")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Course")
            }

        }

    }
}


