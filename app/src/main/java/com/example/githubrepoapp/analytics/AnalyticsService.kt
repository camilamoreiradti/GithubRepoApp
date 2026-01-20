package com.example.githubrepoapp.analytics

interface AnalyticsService {
    fun logRepoItemClick(log: Map<String, String>)
    fun logButtonClick(buttonName: String)
}