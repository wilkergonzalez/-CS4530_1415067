package com.example.httpreqdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunFactDao {
    @Query("SELECT * FROM fun_facts ORDER BY id DESC")
    fun getAllFacts(): Flow<List<FunFact>>

    @Insert
    suspend fun insertFact(funFact: FunFact)

    @Query("DELETE FROM fun_facts")
    suspend fun deleteAll()
}