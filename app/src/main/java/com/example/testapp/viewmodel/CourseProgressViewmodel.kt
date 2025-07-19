package com.example.testapp.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.network.YoutubeApiInstance
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class MyCourseProgress(
    val title: String,
    val thumbnailUrl: String,
    val playlistId: String,
    val watchedCount: Int,
    val totalCount: Int
) {
    val progressPercent: Int
        get() = if (totalCount > 0) (watchedCount * 100 / totalCount) else 0
}

class CourseProgressViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _myCourses = MutableStateFlow<List<MyCourseProgress>>(emptyList())
    val myCourses: StateFlow<List<MyCourseProgress>> = _myCourses

    fun startCourse(playlistId: String, title: String, thumbnailUrl: String) {
        val userId = auth.currentUser?.uid ?: return
        val startedCourse = hashMapOf(
            "title" to title,
            "playlistId" to playlistId,
            "thumbnailUrl" to thumbnailUrl,
            "progress" to 0,
            "watchedVideos" to emptyList<String>()
        )

        db.collection("users").document(userId)
            .collection("myCourses")
            .document(playlistId)
            .set(startedCourse)
            .addOnSuccessListener {
                Log.d("Course", "Started course saved.")
                fetchMyCoursesWithProgress()
            }
            .addOnFailureListener {
                Log.e("Course", "Failed to save course", it)
            }
    }

    // Mark Video Watched
    fun markVideoWatched(playlistId: String, videoId: String) {
        val userId = auth.currentUser?.uid ?: return
        val courseRef = db.collection("users").document(userId)
            .collection("myCourses").document(playlistId)

        courseRef.get().addOnSuccessListener { doc ->
            val watchedVideos = doc.get("watchedVideos") as? List<String> ?: emptyList()
            val updatedList = watchedVideos.toMutableSet().apply { add(videoId) }.toList()
            courseRef.update("watchedVideos", updatedList)
        }
    }

    // Fetch Courses with Progress
    fun fetchMyCoursesWithProgress() {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val snapshot = db.collection("users").document(userId)
                    .collection("myCourses").get().await()

                val coursesList = mutableListOf<MyCourseProgress>()

                for (doc in snapshot.documents) {
                    val title = doc.getString("title") ?: continue
                    val thumbnail = doc.getString("thumbnailUrl") ?: ""
                    val playlistId = doc.getString("playlistId") ?: ""
                    val watched = doc.get("watchedVideos") as? List<String> ?: emptyList()

                    //Fetch YouTube playlist videos count
                    val response = YoutubeApiInstance.api.getPlaylistVideos(
                        playlistId = playlistId,
                        apiKey = "AIzaSyCPdmokjfyr9aOFLDbstTkStZQ_d8BuROo"
                    )

                    val totalVideos = response.items.size

                    coursesList.add(
                        MyCourseProgress(
                            title = title,
                            thumbnailUrl = thumbnail,
                            playlistId = playlistId,
                            watchedCount = watched.size,
                            totalCount = totalVideos
                        )
                    )
                }
                _myCourses.value = coursesList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ✅ Delete Course
    fun deleteCourse(playlistId: String) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId)
            .collection("myCourses")
            .document(playlistId)
            .delete()
            .addOnSuccessListener {
                _myCourses.value = _myCourses.value.filterNot { it.playlistId == playlistId }
            }
    }
}
