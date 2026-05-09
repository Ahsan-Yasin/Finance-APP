package com.example.financetracker.remoteconfig

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
        
        // Define default values
        val defaults = mapOf(
            "welcome_message" to "Welcome to your Finance Tracker!",
            "new_feature_enabled" to true
        )
        remoteConfig.setDefaultsAsync(defaults)
    }

    // NF1 - Fetch and activate values using coroutines
    suspend fun fetchAndActivate(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            false
        }
    }

    fun getWelcomeMessage(): String = remoteConfig.getString("welcome_message")
    fun isNewFeatureEnabled(): Boolean = remoteConfig.getBoolean("new_feature_enabled")
}
