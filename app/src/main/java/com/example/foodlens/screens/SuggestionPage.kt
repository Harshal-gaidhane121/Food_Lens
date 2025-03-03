package com.example.foodlens.screens

import ChatBotScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodlens.FloatingBottomNavigation
import com.example.foodlens.R
import com.example.foodlens.UserViewModel

@Composable
fun SuggestionPage(navHostController: NavHostController,viewModel: UserViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ){
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        ChatBotScreen(viewModel)

        FloatingBottomNavigation(navHostController)
    }

}