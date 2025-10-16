package com.example.coursemanager

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepository private constructor(context: Context) {
    private val database = CourseDatabase.getDatabase(context)
    private val courseDao = database.courseDao()

    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()
        .map { entities ->
            entities.map { it.toCourse() }
        }

    suspend fun insertCourse(course: Course) {
        courseDao.insertCourse(course.toEntity())
    }

    suspend fun deleteCourse(course: Course) {
        courseDao.deleteCourse(course.toEntity())
    }

    suspend fun getCourseById(courseId: Long): Course? {
        return courseDao.getCourseById(courseId)?.toCourse()
    }
    companion object {
        @Volatile
        private var INSTANCE: CourseRepository? = null

        fun getInstance(context: Context): CourseRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CourseRepository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}