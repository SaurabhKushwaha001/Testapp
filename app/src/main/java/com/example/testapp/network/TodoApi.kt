package com.example.testapp.network
import com.example.testapp.model.TodoItem
import retrofit2.http.GET


interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<TodoItem>
}