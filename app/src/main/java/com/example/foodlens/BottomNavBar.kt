package com.example.foodlens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Preview(showSystemUi = true)
@Composable
fun FloatingBottomNavigation(navHostController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf("home", "search", "profile")
    val icons = listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.Person)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp), contentAlignment = Alignment.BottomCenter
    ) {

        Card(

            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth(.8f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, route ->
                    IconButton(
                        onClick = {
//                        selectedItem = index
//                        navController.navigate(route)
                        }, modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                if (selectedItem == index) Color(94, 236, 100, 134) else Color(
                                    255, 255, 255, 0
                                )
                            )
                    ) {
                        Icon(
                            imageVector = icons[index],
                            modifier = Modifier.scale(1.4f),
                            contentDescription = route,
                            tint = colorResource(R.color.green)
                        )
                    }
                }
            }
        }

    }
}

