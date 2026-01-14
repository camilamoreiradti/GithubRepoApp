package com.example.githubrepoapp.analytics

import android.os.Bundle
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class AnalyticsManager @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {
    fun logRepoItemClick(
        repoItem: RepoItem
    ) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_NAME, repoItem.name)
            putString("item_owner", repoItem.owner.name)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)
    }
}