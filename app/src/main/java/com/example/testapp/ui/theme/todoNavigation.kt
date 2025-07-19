package com.example.testapp.ui.theme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.screens.CourseDetailScreen
import com.example.testapp.screens.CourseHomeScreen
import com.example.testapp.screens.CourseOverviewScreen
import com.example.testapp.screens.LoginScreen
import com.example.testapp.screens.MyCoursesScreen
import com.example.testapp.screens.PlayVideoScreen
import com.example.testapp.screens.ProfileScreen
import com.example.testapp.screens.SignupScreen
import com.example.testapp.screens.TodoDetailScreen
import com.example.testapp.screens.TodoHomeScreen
import com.example.testapp.viewmodel.AuthViewModel

@Composable
fun TodoNavigation(authViewModel: AuthViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable(route = "todo_HomeScreen") {
            TodoHomeScreen(navController, authViewModel = authViewModel)
        }
        composable(route= "todoDetail/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            TodoDetailScreen(navController ,title = title ?: "No Title")
        }
        composable (route = "login"){
            LoginScreen(navController, authViewModel)
        }
        composable(route = "signup"){
            SignupScreen(navController, authViewModel)
        }
        composable (route = "profile"){
            ProfileScreen(authViewModel, navController)
        }
        composable (route = "CourseHomeScreen"){
            CourseHomeScreen(navController)
        }
        composable(route = "CourseDetail/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId")
            CourseDetailScreen(navController, playlistId)
        }
        composable("playVideo/{playlistId}/{videoId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            PlayVideoScreen(playlistId, videoId, navController)
        }

        composable("CourseOverview/{playlistId}/{title}/{thumbnailUrl}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val thumbnailUrl = backStackEntry.arguments?.getString("thumbnailUrl") ?: ""

            // URL decode the thumbnail URL
            val decodedThumbnailUrl = try {
                java.net.URLDecoder.decode(thumbnailUrl, "UTF-8")
            } catch (e: Exception) {
                thumbnailUrl
            }

            CourseOverviewScreen(navController, playlistId, decodedThumbnailUrl, title, "This is a brief overview.")
        }
        composable("myCourses") {
            MyCoursesScreen(navController)
        }

    }
}