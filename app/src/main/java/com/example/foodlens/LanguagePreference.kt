package com.example.foodlens

import android.content.Context
import android.content.SharedPreferences

class LanguagePreference(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveLanguage(language: String) {
        prefs.edit().putString("selected_language", language).apply()
    }

    fun getLanguage(): String {
        return prefs.getString("selected_language", "en") ?: "en"  // Default is English
    }
}
