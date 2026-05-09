package com.smd.financeTracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smd.financeTracker.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(repository.getCurrentUser())
    val user: StateFlow<FirebaseUser?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.signInWithEmailPassword(email, password)
            if (result != null) {
                _user.value = result
                onSuccess()
            } else {
                _error.value = "Sign in failed. Please check your credentials."
            }
            _isLoading.value = false
        }
    }

    fun register(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.registerWithEmail(email, password)
            if (result != null) {
                _user.value = result
                onSuccess()
            } else {
                _error.value = "Registration failed. Email might be in use."
            }
            _isLoading.value = false
        }
    }

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.signInWithGoogle(idToken)
            if (result != null) {
                _user.value = result
                onSuccess()
            } else {
                _error.value = "Google sign in failed."
            }
            _isLoading.value = false
        }
    }

    fun logout(onSuccess: () -> Unit) {
        repository.signOut()
        _user.value = null
        onSuccess()
    }
}
