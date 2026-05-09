package com.example.financetracker

import android.app.Application
import com.example.financetracker.remoteconfig.RemoteConfigHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        
        // Initialize Analytics & Crashlytics
        FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Fetch Remote Config on startup
        CoroutineScope(Dispatchers.IO).launch {
            RemoteConfigHelper().fetchAndActivate()
        }
    }
}
