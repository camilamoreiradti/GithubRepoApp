package com.example.githubrepoapp.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val USER_ID = stringPreferencesKey("uid")
    val USER_EMAIL = stringPreferencesKey("user_email")
}