import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.*

fun setAppLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Restart the activity
    val activity = context as? Activity
    activity?.let {
        val intent = it.intent
        it.finish()
        it.startActivity(intent)
    }
}
