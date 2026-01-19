package com.example.githubrepoapp.analytics.firebase

import android.os.Bundle
import com.example.githubrepoapp.analytics.AnalyticsService
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
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
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)
    }

    override fun logButtonClick(buttonName: String) {
        firebaseAnalytics.logEvent("button_click") {
            param("button_name", buttonName)
        }
    }
}