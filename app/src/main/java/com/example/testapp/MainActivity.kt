package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.testapp.ui.theme.TestAppTheme
import com.example.testapp.ui.theme.TodoNavigation
import com.example.testapp.viewmodel.AuthState
import com.example.testapp.viewmodel.AuthViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        // This must be called before super.onCreate()

        FirebaseApp.initializeApp(this)
        splashScreen.setKeepOnScreenCondition {
            authViewModel.authState.value is AuthState.Loading
        }
        enableEdgeToEdge()

        setContent {
            TestAppTheme {
                TodoNavigation(authViewModel = authViewModel)
            }
        }
    }
}