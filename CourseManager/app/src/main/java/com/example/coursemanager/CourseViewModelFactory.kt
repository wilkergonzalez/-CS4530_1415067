package com.example.coursemanager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CourseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            val repository = CourseRepository.getInstance(context)
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}