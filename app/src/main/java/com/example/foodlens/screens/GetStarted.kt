package com.example.foodlens.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodlens.LocalLanguageManager
import com.example.foodlens.R

@Composable
fun GetStarted(navHostController: NavHostController) {
    val languageManager = LocalLanguageManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.shoppinghome),
                contentDescription = null,
                modifier = Modifier.scale(1.8f)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = stringResource(R.string.eat_healthy),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                color = Color(70, 66, 66, 193),
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
            )

            Text(
                text = stringResource(R.string.health_focus),
                modifier = Modifier.padding(10.dp),
                color = Color(70, 66, 66, 133),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 15.dp)
                    .height(60.dp)
                    .fillMaxWidth(.8f)
                    .background(colorResource(R.color.green), CircleShape),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                onClick = {
                    navHostController.navigate("register")
                }
            ) {
                Text(
                    text = stringResource(R.string.get_started),
                    color = Color.White,
                    fontSize = 19.sp
                )
            }
        }


        LanguageDropdown(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 38.dp, bottom = 16.dp, start = 16.dp, end = 20.dp)
        )
    }
}

@Composable
fun LanguageDropdown(modifier: Modifier = Modifier) {
    val languageManager = LocalLanguageManager.current
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf(
        "en" to stringResource(R.string.english),
        "hi" to stringResource(R.string.hindi)
    )
    var selectedLanguage by remember {
        mutableStateOf(
            languages.find { it.first == languageManager.currentLanguage }?.second ?: "English"
        )
    }

    Box(modifier = modifier) {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(Color.White) // White button background
        ) {
            Text(
                text = selectedLanguage,
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            languages.forEach { (code, name) ->
                DropdownMenuItem(
                    text = { Text(name, color = Color.Gray) },
                    onClick = {
                        selectedLanguage = name
                        languageManager.setLanguage(code)
                        expanded = false
                    }
                )
            }
        }
    }
}