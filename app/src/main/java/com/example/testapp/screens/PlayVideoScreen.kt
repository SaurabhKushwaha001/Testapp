package com.example.testapp.screens

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PlayVideoScreen(videoId: String, navController: NavController) {
    val videoUrl = "https://www.youtube.com/embed/$videoId"

    Scaffold(topBar = { TopAppBar(title = { Text("Watch Video",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center) },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }})}) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            600 // fixed height for video player
                        )
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        loadUrl(videoUrl)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // proper height to ensure video is visible
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
