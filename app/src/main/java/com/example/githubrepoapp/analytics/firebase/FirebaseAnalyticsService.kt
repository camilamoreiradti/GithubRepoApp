package com.example.githubrepoapp.analytics.firebase

import android.os.Bundle
import com.example.githubrepoapp.analytics.AnalyticsService
import com.example.githubrepoapp.analytics.LogEvent
import com.example.githubrepoapp.analytics.LogParamName
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject

class FirebaseAnalyticsService @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsService {
    override fun logRepoItemClick(log: Map<String, String>) {
        val bundle = Bundle().apply {
            log.forEach { (key, value) ->
                putString(key, value)
            }
        }
        firebaseAnalytics.logEvent(LogEvent.REPO_ITEM_CLICK.toString(), bundle)
    }

    override fun logButtonClick(buttonName: String) {
        firebaseAnalytics.logEvent(LogEvent.BUTTON_CLICK.toString()) {
            param(LogParamName.BUTTON_NAME.toString(), buttonName)
        }
    }
}