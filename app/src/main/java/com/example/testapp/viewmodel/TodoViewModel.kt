package com.example.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.TodoItem
import com.example.testapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                val todos = RetrofitInstance.api.getTodos()
                _todoList.value = todos
            } catch (e: Exception) {
                // Handle error here
                e.printStackTrace()
            }
        }
    }
}
