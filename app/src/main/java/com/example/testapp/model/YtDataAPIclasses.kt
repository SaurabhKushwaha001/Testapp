package com.example.testapp.model

data class YoutubeResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val snippet: Snippet,
    val contentDetails: ContentDetails
)

data class Snippet(
    val title: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(
    val medium: ThumbnailInfo
)

data class ThumbnailInfo(
    val url: String
)

data class ContentDetails(
    val videoId: String
)
