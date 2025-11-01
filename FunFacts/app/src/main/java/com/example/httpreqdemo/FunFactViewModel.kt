package com.example.httpreqdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FunFactViewModel(private val repository: FunFactRepository) : ViewModel() {

    // Convert Flow to StateFlow for Compose
    val funFactList: StateFlow<List<FunFact>> = repository.allFacts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Fetch a new fact
    fun fetchNewFact() {
        viewModelScope.launch {
            try {
                repository.fetchAndStoreFact()
            } catch (e: Exception) {
                // Error already logged in repository
            }
        }
    }
}