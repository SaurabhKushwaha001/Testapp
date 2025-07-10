package com.example.testapp.network
import com.example.testapp.model.YoutubeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {
    @GET("playlistItems")
    suspend fun getPlaylistVideos(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 25,
        @Query("key") apiKey: String
    ): YoutubeResponse
}