package com.example.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.VideoItem
import com.example.testapp.network.YoutubeApiInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseDetailViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos: StateFlow<List<VideoItem>> = _videos

    private val apiKey = "AIzaSyCPdmokjfyr9aOFLDbstTkStZQ_d8BuROo"

    fun fetchVideos(playlistId: String) {
        viewModelScope.launch {
            try {
                val response = YoutubeApiInstance.api.getPlaylistVideos(
                    playlistId = playlistId,
                    apiKey = apiKey
                )
                _videos.value = response.items
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
