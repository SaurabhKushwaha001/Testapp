package com.example.testapp.viewmodel
import android.window.SplashScreen
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel(){
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState : StateFlow<AuthState> = _authState

    init {

        _authState.value = AuthState.Loading
        checkAuthStatus()

    }
    fun checkAuthStatus() {
            _authState.value = if (auth.currentUser != null) {
                AuthState.Authenticated
            } else {
                AuthState.Unauthenticated
            }

    }
    fun signUp(email: String , password : String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Invalid email or password")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Sign in failed, try again.")
                }

            }

    }
    fun login (email: String , password : String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Invalid email or password")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                        _authState.value = AuthState.Error(task.exception?.message?:"Sign in failed, try again.")
                }
            }


    }
    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
    fun afterErrorState(){
        if (_authState.value is AuthState.Error){
            if(auth.currentUser == null){
                _authState.value = AuthState.Unauthenticated
            }
        }
    }
}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String): AuthState()
}