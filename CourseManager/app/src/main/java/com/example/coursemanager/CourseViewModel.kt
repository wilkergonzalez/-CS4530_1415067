package com.example.coursemanager

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class CourseViewModel : ViewModel() {
    private val _courses = mutableStateOf<List<Course>>(emptyList())
    val courses: State<List<Course>> = _courses

    private val _selectedCourse = mutableStateOf<Course?>(null)
    val selectedCourse: State<Course?> = _selectedCourse

    private val _showAddDialog = mutableStateOf(false)
    val showAddDialog: State<Boolean> = _showAddDialog

    fun addCourse(department: String, courseNumber: String, location: String) {
        val newCourse = Course(
            department = department.trim(),
            courseNumber = courseNumber.trim(),
            location = location.trim()
        )
        _courses.value = _courses.value + newCourse
    }

    fun deleteCourse(course: Course) {
        _courses.value = _courses.value.filter { it.id != course.id }
        if (_selectedCourse.value?.id == course.id) {
            _selectedCourse.value = null
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