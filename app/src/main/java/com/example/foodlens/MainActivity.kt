package com.example.foodlens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodlens.screens.GetStarted
import com.example.foodlens.screens.Home
import com.example.foodlens.screens.Register
import com.example.foodlens.ui.theme.FoodLensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodLensTheme {
                val context = LocalContext.current
                ProvideLanguageManager(context = context) {
                    val controller: NavController = rememberNavController()
                    Navigation(navController = controller, context = context)
                }
            }
        }
    }
}