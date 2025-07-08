package com.example.testapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.testapp.ui.theme.TestAppTheme
import com.example.testapp.ui.theme.TodoNavigation
import com.example.testapp.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         val authViewModel: AuthViewModel by viewModels()
        setContent {
            TestAppTheme {
                TodoNavigation(authViewModel = authViewModel)
            }
        }
    }
}

