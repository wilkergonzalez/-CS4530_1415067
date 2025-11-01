package com.example.httpreqdemo

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = "fun_facts")
data class FunFact(
    @PrimaryKey(autoGenerate = true)
    @Transient
    val id: Int = 0,
    val text: String,
    val source_url: String? = null
)