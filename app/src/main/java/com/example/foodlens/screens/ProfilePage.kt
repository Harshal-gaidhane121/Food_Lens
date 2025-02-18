package com.example.foodlens.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodlens.FloatingBottomNavigation
import com.example.foodlens.R

@Composable
fun ProfilePage(navHostController: NavHostController) {
    val context = LocalContext.current

    val userMobileNO = getCurrentUser(context = context )


    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){

        Image(
            painterResource(R.drawable.bg1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (userMobileNO != null) {
            Text(text= userMobileNO, modifier = Modifier.padding(40.dp))
        }

        FloatingBottomNavigation(navHostController)
    }


}

fun getCurrentUser(context: Context): String? {
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPref.getString("LOGGED_IN_USER", null)
}