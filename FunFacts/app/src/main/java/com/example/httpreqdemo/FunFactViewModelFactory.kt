package com.example.httpreqdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FunFactViewModelFactory(
    private val repository: FunFactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FunFactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FunFactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}