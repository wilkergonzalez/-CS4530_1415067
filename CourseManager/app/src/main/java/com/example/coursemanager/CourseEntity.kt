package com.example.coursemanager

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "courses")
data class CourseEntity(@PrimaryKey(autoGenerate = true)
                        val id: Long = 0,
                        val department: String,
                        val courseNumber: String,
                        val location: String){
    fun toCourse(): Course = Course( id = id,
        department = department,
        courseNumber = courseNumber,
        location = location)
}

fun Course.toEntity(): CourseEntity = CourseEntity(
    id = if (id == 0L) 0 else id,
    department = department,
    courseNumber = courseNumber,
    location = location
)