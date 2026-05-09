package com.smd.financeTracker.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    suspend fun <T : Any> saveData(collection: String, documentId: String, data: T) {
        db.collection(collection).document(documentId).set(data).await()
    }

    suspend fun <T : Any> getData(collection: String, clazz: Class<T>): List<T> {
        return db.collection(collection).get().await().toObjects(clazz)
    }

    fun <T : Any> syncData(collection: String, clazz: Class<T>): Flow<List<T>> {
        return db.collection(collection).snapshots().map { snapshot ->
            snapshot.toObjects(clazz)
        }
    }

    // Example for "users" and "transactions" relationship
    fun syncUserTransactions(userId: String): Flow<List<Map<String, Any>>> {
        return db.collection("users").document(userId).collection("transactions")
            .snapshots().map { snapshot ->
                snapshot.documents.mapNotNull { it.data }
            }
    }
}
