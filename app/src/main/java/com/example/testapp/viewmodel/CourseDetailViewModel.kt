package com.example.testapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.VideoItem
import com.example.testapp.network.YoutubeApiInstance
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CourseDetailViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos: StateFlow<List<VideoItem>> = _videos

    private val _watchedVideos = MutableStateFlow<Set<String>>(emptySet())
    val watchedVideos: StateFlow<Set<String>> = _watchedVideos

    private val apiKey = "AIzaSyCPdmokjfyr9aOFLDbstTkStZQ_d8BuROo"
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    fun fetchVideos(playlistId: String) {
        viewModelScope.launch {
            try {
                val response = YoutubeApiInstance.api.getPlaylistVideos(
                    playlistId = playlistId,
                    apiKey = apiKey
                )
                Log.d("YTApi_Response", response.toString())
                _videos.value = response.items

                // Also fetch watched videos for this playlist
                fetchWatchedVideos(playlistId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchWatchedVideos(playlistId: String) {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val doc = db.collection("users").document(userId)
                    .collection("myCourses")
                    .document(playlistId)
                    .get()
                    .await()

                val watchedList = doc.get("watchedVideos") as? List<String> ?: emptyList()
                _watchedVideos.value = watchedList.toSet()
            } catch (e: Exception) {
                Log.e("CourseDetail", "Error fetching watched videos", e)
            }
        }
    }
}