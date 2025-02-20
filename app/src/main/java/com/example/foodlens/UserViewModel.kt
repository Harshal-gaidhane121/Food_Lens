package com.example.foodlens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> = _selectedCategory

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    // Register a new user
    fun registerUser(user: User, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            userRepository.registerUser(user)
            onComplete(true)  // Registration successful
        }
    }
    fun loginUser(mobile: String, password: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userRepository.loginUser(mobile, password)
            onComplete(result)  // true if login is successful, else false
        }
    }

    // Check if mobile is already registered
    fun isMobileRegistered(mobile: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isRegistered = userRepository.isMobileRegistered(mobile)
            onComplete(isRegistered)  // true if mobile is already registered
        }
    }
}
