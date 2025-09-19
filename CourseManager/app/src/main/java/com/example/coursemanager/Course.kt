package com.example.coursemanager

data class Course(
    val id: Long = System.currentTimeMillis(),
    val department: String,
    val courseNumber: String,
    val location: String
) {
    val displayName: String
        get() = "$department $courseNumber"
}