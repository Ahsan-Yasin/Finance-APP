package com.example.financetracker.notifications

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            NotificationHelper(applicationContext).showNotification(
                it.title ?: "Finance Tracker",
                it.body ?: "New update available"
            )
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "New Token: $token")
        saveTokenToFirestore(token)
    }

    private fun saveTokenToFirestore(token: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance().collection("users").document(userId)
            .update("fcmToken", token)
            .addOnFailureListener { e ->
                Log.e("FCM", "Token update failed", e)
            }
    }
}
