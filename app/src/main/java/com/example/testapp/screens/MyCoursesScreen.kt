
package com.example.testapp.screens
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.lazy.items
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.viewmodel.CourseProgressViewModel
import com.example.testapp.viewmodel.MyCourseProgress
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCoursesScreen(
    navController: NavController,
    viewModel: CourseProgressViewModel = viewModel()
) {
    val myCourses by viewModel.myCourses.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMyCoursesWithProgress()
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Course Videos",
        modifier = Modifier.fillMaxWidth(),) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
            }
        }) }){ padding ->
        if (myCourses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No courses started yet")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(myCourses) { course ->
                    CourseProgressCard(
                        course = course,
                        onClick = { navController.navigate("CourseDetail/${course.playlistId}") },
                        onDelete = { viewModel.deleteCourse(course.playlistId) }
                    )
                }
            }
        }
    }
}

@Composable
fun CourseProgressCard(course: MyCourseProgress, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = course.title, fontWeight = FontWeight.Bold)
            Text("${course.watchedCount}/${course.totalCount} videos watched")

            LinearProgressIndicator(
                progress = if (course.totalCount > 0)
                    course.watchedCount / course.totalCount.toFloat()
                else 0f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onClick) {
                    Text("Open")
                }
                Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                    Text("Remove")
                }
            }
        }
    }
}
