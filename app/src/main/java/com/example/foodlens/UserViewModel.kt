package com.example.foodlens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    var hasShownGreeting by mutableStateOf(false)
        private set

    // Function to set the greeting as shown
    fun markGreetingAsShown() {
        hasShownGreeting = true
    }

    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> = _selectedCategory

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

}
