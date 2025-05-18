package com.example.safetynet

fun UserTitle.defaultDestination(): String = when (this) {
    UserTitle.Patient -> "home"
    UserTitle.Caretaker -> "patient-list"
}