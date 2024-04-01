package com.example.unspoken.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class MainRepository {

    private val db = Firebase.firestore

    suspend fun getMainFeeds(): List<Feed> {
        return db
            .collection("subjects")
            .get()
            .await()
            .toObjects(Feed()::class.java)
    }
}

data class Feed(
    val title: String = ""
)
