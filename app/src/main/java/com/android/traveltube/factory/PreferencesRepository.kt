package com.android.traveltube.factory

import android.content.SharedPreferences

class PreferencesRepository(private val sharedPreferences: SharedPreferences) {
    private val favoriteKey = "saveFavoritesData"
}