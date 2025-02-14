package com.example.foodlens

import androidx.compose.runtime.Composable
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
fun Navigation(navController: NavController) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = "home",
    ) {
        composable("home") {
            Home(navController)
        }
        composable("getStarted") {
            GetStarted(navController)
        }
        composable("loginPage") {
            LoginPage(navController)
        }
        composable("register") {
            Register(navController)
        }
        composable("home") {
            Home(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }

    }

}