package com.example.testapp.datastore
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "todo_prefs")

class TodoPreferences(private val context: Context){

    fun getTodoCompletedState(id: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("todo_completed_$id")
        return context.dataStore.data.map { prefs ->
            prefs[key] ?: false
        }

    }


    suspend fun setTodoCompletedState(id: Int, completed: Boolean) {
        val key = booleanPreferencesKey("todo_completed_$id")
        context.dataStore.edit { prefs ->
            prefs[key] = completed
        }
    }
}