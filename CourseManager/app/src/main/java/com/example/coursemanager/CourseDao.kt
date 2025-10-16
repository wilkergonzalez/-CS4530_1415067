package com.example.coursemanager

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY department, courseNumber")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Insert
    suspend fun insertCourse(course: CourseEntity): Long

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: Long): CourseEntity?
}