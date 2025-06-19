package com.example.testapp.ui
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.viewmodel.TodoViewModel

@Composable
    fun test2() {var topText by remember { mutableStateOf("welcome to list") }
        val num = listOf("2","3","4","5","2","3","4","5","2","3","4","5","2","3","4","5","2","3","4","5","2","3","4","5")
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp), contentAlignment = Alignment.Center){
                Text(text = topText)
            }
            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)) {

                items(num) {
                        number->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = number, modifier = Modifier.weight(1F))
                        Button(onClick = { topText = number }, modifier = Modifier.weight(1F)) {
                            Text(text = "show")
                        }
                    }
                }
            }
        }

    }


