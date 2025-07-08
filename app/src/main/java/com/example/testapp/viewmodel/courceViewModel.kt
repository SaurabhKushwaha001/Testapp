package com.example.testapp.viewmodel
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import com.example.testapp.model.Course

class CourseViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _courses = MutableStateFlow<List< Course>>(emptyList())
    val courses : StateFlow<List<Course>> = _courses

    init {
        fetchCourses()
    }
    private fun fetchCourses(){
        db.collection("cources").get()
            .addOnSuccessListener {result->
                val courseList = result.mapNotNull { doc ->
                    Course(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        thumbnailUrl = doc.getString("thumbnailUrl") ?: "",
                        playlistId = doc.getString("playlistId") ?: ""
                    )
                }
                _courses.value = courseList
            }
        .addOnFailureListener {
                Log.e("Firestore", "Error fetching courses", it)
            }
    }
}
