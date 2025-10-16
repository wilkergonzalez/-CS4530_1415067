package com.example.coursemanager

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



class CourseViewModel(private val repository: CourseRepository
) : ViewModel() {
    val courses: StateFlow<List<Course>> = repository.allCourses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    private val _selectedCourse = mutableStateOf<Course?>(null)
    val selectedCourse: State<Course?> = _selectedCourse

    private val _showAddDialog = mutableStateOf(false)
    val showAddDialog: State<Boolean> = _showAddDialog

    fun addCourse(department: String, courseNumber: String, location: String) {
        viewModelScope.launch {
            val newCourse = Course(
                department = department.trim(),
                courseNumber = courseNumber.trim(),
                location = location.trim()
            )
            repository.insertCourse(newCourse)
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            repository.deleteCourse(course)
            if (_selectedCourse.value?.id == course.id) {
                _selectedCourse.value = null
            }
        }
    }

    fun selectCourse(course: Course) {
        _selectedCourse.value = course
    }

    fun clearSelection() {
        _selectedCourse.value = null
    }

    fun showAddDialog() {
        _showAddDialog.value = true
    }

    fun hideAddDialog() {
        _showAddDialog.value = false
    }
}