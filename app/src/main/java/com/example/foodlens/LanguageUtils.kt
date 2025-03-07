
package com.example.foodlens

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.*

class LanguageManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    var currentLanguage by mutableStateOf(getSavedLanguage())
        private set

    private fun getSavedLanguage(): String {
        return prefs.getString("selected_language", "en") ?: "en"
    }

    fun setLanguage(languageCode: String) {
        currentLanguage = languageCode
        prefs.edit().putString("selected_language", languageCode).apply()

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources: Resources = context.resources
        val config: Configuration = Configuration(resources.configuration) // Clone current config
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics) // Update Resources

        // Restart activity to apply changes
        if (context is ComponentActivity) {
            context.recreate()
        } else {
            throw IllegalStateException("LanguageManager requires a ComponentActivity context")
        }
    }

    // Add this to apply the locale on initialization
    init {
        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

val LocalLanguageManager = compositionLocalOf<LanguageManager> {
    error("No LanguageManager provided")
}

@Composable
fun ProvideLanguageManager(context: Context, content: @Composable () -> Unit) {
    val languageManager = remember { LanguageManager(context) }
    CompositionLocalProvider(LocalLanguageManager provides languageManager) {
        content()
    }
}