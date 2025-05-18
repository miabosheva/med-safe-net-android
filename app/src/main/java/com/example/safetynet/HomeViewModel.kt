package com.example.safetynet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class UserTitle {
    Caretaker, Patient
}

class HomeViewModel : ViewModel() {
    var userTitle by mutableStateOf(UserTitle.Caretaker)
        private set

    fun setUserType(type: UserTitle) {
        userTitle = type
    }
}

