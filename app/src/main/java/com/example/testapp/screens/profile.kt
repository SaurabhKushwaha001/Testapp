package com.example.testapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testapp.R
import com.example.testapp.viewmodel.AuthState
import com.example.testapp.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(authViewModel: AuthViewModel, navController: NavController) {

    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    Column(modifier = Modifier.padding(top = 38.dp, start = 10.dp).fillMaxWidth()) {
        IconButton(onClick = {navController.popBackStack()}) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "Back")
        }
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Log out", fontSize = 32.sp)
            TextButton(onClick = {
                authViewModel.signOut()
//            navController.navigate("login")
            }) {
                Text("Log Out")
            }
        }
    }
}