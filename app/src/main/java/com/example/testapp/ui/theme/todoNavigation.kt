package com.example.testapp.ui.theme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.screens.LoginScreen
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
    }
}