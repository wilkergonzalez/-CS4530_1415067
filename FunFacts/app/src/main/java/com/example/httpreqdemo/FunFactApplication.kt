package com.example.httpreqdemo

import android.app.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FunFactApplication : Application() {
    // Lazy initialization of database
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    // Lazy initialization of Ktor HTTP client
    val httpClient: HttpClient by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // Lazy initialization of repository (singleton)
    val repository: FunFactRepository by lazy {
        FunFactRepository(database.funFactDao(), httpClient)
    }
}