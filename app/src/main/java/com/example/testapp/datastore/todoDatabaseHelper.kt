package com.example.testapp.datastore

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testapp.model.TodoItem

class TodoDatabaseHelper(context : Context): SQLiteOpenHelper(context, "myDatabase", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE todos (id INTEGER PRIMARY KEY,userId INTEGER, title TEXT, completed INTEGER)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS todos")
        onCreate(db)
    }

    fun insertTodo(todos:List<TodoItem>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (todo in todos) {
                val values = ContentValues().apply {
                    put("id", todo.id)
                    put("userId", todo.userId)
                    put("title", todo.title)
                    put("completed", if (todo.completed) 1 else 0)
                }
                db.insertWithOnConflict("todos", null, values, SQLiteDatabase.CONFLICT_REPLACE)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
}
    fun getAllTodos(): List<TodoItem> {
        val list = mutableListOf<TodoItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM todos", null)
        while (cursor.moveToNext()) {
            list.add(
                TodoItem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow("userId")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    completed = cursor.getInt(cursor.getColumnIndexOrThrow("completed")) == 1
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateTodoCompleted(id: Int, completed: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("completed", if (completed) 1 else 0)
        }
        db.update("todos", values, "id=?", arrayOf(id.toString()))
    }
}