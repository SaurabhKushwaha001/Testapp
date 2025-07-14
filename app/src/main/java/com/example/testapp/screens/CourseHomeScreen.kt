package com.example.testapp.screens
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.viewmodel.CourseViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CourseHomeScreen(navController: NavController, viewModel: CourseViewModel = viewModel()) {
    val courses by viewModel.courses.collectAsState()
    Scaffold(topBar = { TopAppBar(title = {
        Text (text = "Courses",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center)
    },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("profile")
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "profile"
                )
            }
        }) }) { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(8.dp)
            .padding(innerPadding)) {
            items(courses) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("CourseOverview/${course.playlistId}/${course.title}/${URLEncoder.encode(course.thumbnailUrl, StandardCharsets.UTF_8.toString())}")
                        },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(course.thumbnailUrl),
                            contentDescription = "Thumbnail",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(12.dp)),
//                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = course.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = "Tap to explore videos",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
