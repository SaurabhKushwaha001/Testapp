package com.example.testapp.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.R
import com.example.testapp.viewmodel.AuthState
import com.example.testapp.viewmodel.AuthViewModel
import com.example.testapp.viewmodel.TodoViewModel

@Composable
fun TodoHomeScreen(navController: NavController, todoViewModel: TodoViewModel = viewModel(), authViewModel: AuthViewModel) {
    val todos by todoViewModel.todoList.collectAsState()
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else-> Unit
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, top = 15.dp),
            horizontalArrangement = Arrangement.Center,
        ) {

            IconButton(onClick = {
                navController.navigate("profile")
            }) {
                Image(painter = painterResource(id = R.drawable.account), contentDescription = "profile", modifier = Modifier.weight(2f).size(20.dp))
            }
            Text(text ="To Do List",
                modifier = Modifier.padding(bottom = 8.dp, top = 15.dp).
                weight(4f),
                style = MaterialTheme.typography.headlineMedium)

        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(todos) { todo ->
                var isChecked by remember(todo.id) { mutableStateOf(todo.completed) }
                Card(modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
                    .clickable {navController.navigate(route = "todoDetail/${todo.title}") })
                {
                    Row(modifier = Modifier.padding(8.dp)) {

                        Column(modifier = Modifier.weight(4f)) {
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("ID")
                                }
                                append(": ${todo.id}")
                            }, modifier = Modifier.padding(start = 8.dp))
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Title")
                                }
                                append(": ${todo.title}")
                            }, modifier = Modifier.padding(start = 8.dp))
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Completed")
                                }
                                append(": ${if (todo.completed) "Yes" else "No"}")
                            }, modifier = Modifier.padding(start = 8.dp))
                        }
                        Checkbox( modifier = Modifier.align(Alignment.CenterVertically)
                            .weight(1f),
                            checked = isChecked,
                            onCheckedChange = { newValue ->
                                isChecked = newValue
                                todoViewModel.updateCompleted(todo.id,newValue) }
                        )
                    }
                }
            }
        }
    }
}

