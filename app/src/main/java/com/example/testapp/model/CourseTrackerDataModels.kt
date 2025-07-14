package com.example.testapp.model

data class CourseProgress(
    val playlistId: String = "",
    val title: String = "",
    val thumbnailUrl: String = "",
    val totalVideos: Int = 0,
    val watchedVideos: Int = 0,
    val startedAt: Long = 0,
    val lastWatchedAt: Long = 0,
    val progressPercentage: Float = 0f
)

data class WatchedVideo(
    val videoId: String = "",
    val title: String = "",
    val watchedAt: Long = 0,
    val watchDuration: Long = 0 // in seconds
)