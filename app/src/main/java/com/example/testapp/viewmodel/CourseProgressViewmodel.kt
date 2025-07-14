package com.example.testapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class CourseProgressViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    fun startCourse(playlistId: String, title: String, thumbnailUrl: String) {
        val userId = auth.currentUser?.uid ?: return
        val startedCourse = hashMapOf(
            "title" to title,
            "playlistId" to playlistId,
            "thumbnailUrl" to thumbnailUrl,
            "progress" to 0
        )
        db.collection("users").document(userId)
            .collection("myCourses")
            .document(playlistId)
            .set(startedCourse)
            .addOnSuccessListener {
                Log.d("Course", "Started course saved.")
            }
            .addOnFailureListener {
                Log.e("Course", "Failed to save course", it)
            }
    }
}
