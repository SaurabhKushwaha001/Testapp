package com.example.testapp.viewmodel
import kotlinx.coroutines.flow.Flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.TodoItem
import com.example.testapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testapp.datastore.TodoPreferences


class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList

    private val preferences = TodoPreferences(application.applicationContext)

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                val todos = RetrofitInstance.api.getTodos()
                _todoList.value = todos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCompletedState(id: Int): Flow<Boolean> {
        return preferences.getTodoCompletedState(id)
    }

    fun setCompletedState(id: Int, completed: Boolean) {
        viewModelScope.launch {
            preferences.setTodoCompletedState(id, completed)
        }
    }
}
