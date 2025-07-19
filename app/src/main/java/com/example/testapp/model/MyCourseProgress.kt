package com.example.testapp.model

data class MyCourseProgress(
    val title: String,
    val thumbnailUrl: String,
    val playlistId: String,
    val watchedCount: Int,
    val totalCount: Int
)