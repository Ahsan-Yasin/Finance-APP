package com.smd.financeTracker.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.tasks.await

class RemoteConfigHelper {
    private val remoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf(
            "welcome_message" to "Welcome to Finance Tracker!",
            "feature_analytics_enabled" to true
        ))
    }

    suspend fun fetchAndActivate(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            false
        }
    }

    fun getWelcomeMessage(): String = remoteConfig.getString("welcome_message")
    fun isAnalyticsEnabled(): Boolean = remoteConfig.getBoolean("feature_analytics_enabled")
}
