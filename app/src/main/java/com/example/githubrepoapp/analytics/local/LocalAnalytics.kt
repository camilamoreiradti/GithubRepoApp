package com.example.githubrepoapp.analytics.local

import android.util.Log
import com.example.githubrepoapp.analytics.AnalyticsService
import javax.inject.Inject

class LocalAnalytics @Inject constructor() : AnalyticsService {
    override fun logRepoItemClick(log: Map<String, String>) {
        Log.i("LOG EVENT", "REPO_ITEM_CLICK: $log")
    }

    override fun logButtonClick(buttonName: String) {
        Log.i("LOG EVENT", "BUTTON_CLICK: $buttonName")
    }
}