package com.example.testapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.screens.TodoDetailScreen
import com.example.testapp.screens.TodoHomeScreen

@Composable
fun TodoNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "todo_HomeScreen") {
        composable(route = "todo_HomeScreen") {
            TodoHomeScreen(navController)
        }
        composable(route= "todoDetail/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            TodoDetailScreen(title = title ?: "No Title")
        }

    }
}