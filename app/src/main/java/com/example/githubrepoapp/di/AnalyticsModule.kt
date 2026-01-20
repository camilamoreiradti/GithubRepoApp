package com.example.githubrepoapp.di

import com.example.githubrepoapp.BuildConfig
import com.example.githubrepoapp.analytics.AnalyticsService
import com.example.githubrepoapp.analytics.firebase.FirebaseAnalyticsService
import com.example.githubrepoapp.analytics.local.LocalAnalytics
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    fun providesFirebaseAnalytics(): FirebaseAnalytics {
        return Firebase.analytics
    }

    @Provides
    fun providesAnalyticsService(
        firebaseAnalytics: FirebaseAnalytics
    ): AnalyticsService {
        return if (BuildConfig.LOCAL_ANALYTICS) {
            LocalAnalytics()
        } else {
            FirebaseAnalyticsService(firebaseAnalytics)
        }
    }
}
