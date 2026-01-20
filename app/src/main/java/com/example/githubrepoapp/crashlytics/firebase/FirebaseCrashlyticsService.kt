package com.example.githubrepoapp.crashlytics.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.setCustomKeys
import javax.inject.Inject

class FirebaseCrashlyticsService @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics
) {
    fun logDataLoadCrash(log: Map<String, String>, throwable: Throwable) {
        firebaseCrashlytics.setCustomKeys {
            log.forEach{ (key, value) ->
                key(key, value)
            }
        }
        firebaseCrashlytics.recordException(throwable)
    }
}