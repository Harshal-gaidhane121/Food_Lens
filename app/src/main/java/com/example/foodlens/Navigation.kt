package com.example.foodlens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodlens.screens.GetStarted
import com.example.foodlens.screens.Home
import com.example.foodlens.screens.LoginPage
import com.example.foodlens.screens.ProfileScreen
import com.example.foodlens.screens.Register
import com.example.foodlens.screens.SearchScreen
@Composable
fun Navigation(navController: NavController,context: Context) {

    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(context) // Pass the factory
    )
    val startDestination = if (isLoggedIn) "home" else "loginPage"

    NavHost(
        navController = navController as? NavHostController ?: return,
        startDestination = startDestination,
    ) {
        composable("getStarted") { GetStarted(navController) }
        composable("loginPage") { LoginPage(navController, userViewModel) }
        composable("register") { Register(navController, userViewModel) }
        composable("home") { Home(navController) }
        composable("search") { SearchScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}


