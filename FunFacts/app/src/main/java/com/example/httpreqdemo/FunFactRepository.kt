package com.example.httpreqdemo

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class FunFactRepository(
    private val dao: FunFactDao,
    private val client: HttpClient
) {
    // Get all facts from local database
    val allFacts: Flow<List<FunFact>> = dao.getAllFacts()

    // Fetch a new fact from API and store it in database
    suspend fun fetchAndStoreFact() {
        try {
            val responseFact: FunFact = client.get("https://uselessfacts.jsph.pl/random.json?language=en").body()
            // Insert into database
            dao.insertFact(responseFact)
            Log.d("FunFactRepository", "Fetched and stored: ${responseFact.text}")
        } catch (e: Exception) {
            Log.e("FunFactRepository", "Error fetching fact", e)
            throw e
        }
    }
}