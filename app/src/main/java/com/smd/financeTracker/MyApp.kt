package com.smd.financeTracker

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase Analytics and Crashlytics
        FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
