package com.example.financetracker.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    // PART 2.2 - Sync user data (real-time listener)
    fun <T : Any> syncUserData(collection: String, clazz: Class<T>): Flow<List<T>> {
        return db.collection(collection).snapshots().map { snapshot ->
            snapshot.toObjects(clazz)
        }
    }

    // PART 2.2 - Save data using coroutines
    suspend fun <T : Any> saveData(collection: String, documentId: String, data: T) {
        db.collection(collection).document(documentId).set(data).await()
    }

    // PART 2.2 - Get data using coroutines
    suspend fun <T : Any> getData(collection: String, clazz: Class<T>): List<T> {
        return db.collection(collection).get().await().toObjects(clazz)
    }

    // Logical relationship: users and their transactions
    suspend fun saveUserTransaction(userId: String, transactionId: String, transaction: Any) {
        db.collection("users").document(userId)
            .collection("transactions").document(transactionId)
            .set(transaction).await()
    }
}
