package com.example.unspoken.data.repository

import com.example.unspoken.domain.MainFeedItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class MainRepository {

    private val db = Firebase.firestore
    private var current = db.collection("subjects")
        .limit(20)

    suspend fun getMainFeeds(): List<MainFeedItem> {
        return current
            .get()
//            .addOnSuccessListener { documentSnapshots ->
//                val lastVisible = documentSnapshots.documents.last()
//                current = db.collection("subjects")
//                    .startAfter(lastVisible)
//                    .limit(20)
//            }
            .await()
            .toObjects(MainFeedItem.SimpleFeed()::class.java)
    }
}

data class Feed(
    val title: String = "",
    val authorID: String = "",
    val feedId: String = ""
)
