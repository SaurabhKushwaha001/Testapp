package com.example.testapp.screens
import android.widget.Toast
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testapp.R
import com.example.testapp.viewmodel.AuthState
import com.example.testapp.viewmodel.AuthViewModel
@Composable
fun SignupScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value){
            is AuthState.Authenticated -> navController.navigate("CourseHomeScreen") {
                launchSingleTop = true
                popUpTo(0)
            }
            is AuthState.Error ->{ Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                authViewModel.afterErrorState()}
            else -> Unit
        }

    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 170.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource( id = R.drawable.person_24),
            contentDescription = "signup image",
            modifier = Modifier.size(70.dp)

        )

        Spacer( modifier = Modifier.height(5.dp))
        Text(text = "User SignUp", fontSize = 32.sp)

        Spacer( modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") })
        Spacer( modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
        Spacer( modifier = Modifier.height(16.dp))
        Button(onClick = {
            authViewModel.signUp(email, password)
        }) {
            Text (text = "SignUp")
        }
        TextButton(onClick = {navController.navigate(route = "login")}) {
            Text (text = "Already have an account? Login")
        }
    }
}