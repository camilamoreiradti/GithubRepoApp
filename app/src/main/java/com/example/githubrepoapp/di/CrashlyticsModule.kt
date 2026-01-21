package com.example.githubrepoapp.di

import com.example.githubrepoapp.crashlytics.firebase.FirebaseCrashlyticsService
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CrashlyticsModule {

    @Provides
    fun providesFirebaseCrashlytics(): FirebaseCrashlytics {
        return Firebase.crashlytics
    }

    @Provides
    fun providesFirebaseCrashlyticsService(
        firebaseCrashlytics: FirebaseCrashlytics
    ): FirebaseCrashlyticsService {
        return FirebaseCrashlyticsService(firebaseCrashlytics)
    }
}