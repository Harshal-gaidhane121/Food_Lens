package com.example.foodlens.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodlens.R
import com.example.foodlens.networks.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import setAppLocale

@Composable
fun Register(navHostController: NavHostController) {
    var mobileNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var languageKey by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val apiService = RegisterRetrofitClient.apiService

    Image(
        painter = painterResource(R.drawable.background2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        ExitDialogBox(context)
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = stringResource(R.string.register),
            color = Color(70, 66, 66, 193),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
        )

        Spacer(modifier = Modifier.height(40.dp))

        TransparentTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.name),
            icon = Icons.Default.Person
        )

        TransparentGenderDropdown(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it },
            icon = Icons.Default.Person
        )

        TransparentTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(R.string.email),
            icon = Icons.Default.Email
        )

        TransparentTextField(
            value = mobileNo,
            onValueChange = { mobileNo = it },
            placeholder = stringResource(R.string.mobile_no),
            isNumberKeyboard = true,
            icon = Icons.Default.Phone
        )

        TransparentTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            icon = Icons.Default.Lock
        )
        key(languageKey) {  // This ensures recomposition when the language changes
            ChangeLanguage()
            // Other UI elements
        }

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(.8f)
                .background(colorResource(R.color.green), CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = {
//                if (mobileNo.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty() || selectedGender.isEmpty()) {
//                    errorMessage = stringResource(R.string.incomplete_credentials)
//                } else if (mobileNo.length < 10) {
//                    errorMessage = stringResource(R.string.invalid_mobile_number)
//                } else {
//                    coroutineScope.launch {
//                        try {
//                            val request = RegisterRequest(name, gender = selectedGender, email, mobileNo, password)
//                            val response = apiService.registerUser(request)
//
//                            if (response.isSuccessful) {
//                                response.body()?.let { body ->
//                                    body.message?.let {
//                                        showToast(context, it)
//                                        navHostController.navigate("loginPage") {
//                                            popUpTo("loginPage") { inclusive = true }
//                                        }
//                                    }
//                                }
//                            } else {
//                                val errorBody = response.errorBody()?.string()
//                                errorMessage = if (!errorBody.isNullOrEmpty()) {
//                                    JSONObject(errorBody).getString("message")
//                                } else {
//                                    stringResource(R.string.registration_failed)
//                                }
//                            }
//                        } catch (e: Exception) {
//                            errorMessage = stringResource(R.string.error_occurred, e.message ?: "Unknown error")
//                        }
//                    }
//                }
            }
        ) {
            Text(
                text = stringResource(R.string.register),
                fontSize = 19.sp,
                color = Color.White
            )
        }

        // Display error message if it exists
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.already_have_account), color = Color(1, 1, 1, 122))

            TextButton(
                onClick = {
                    navHostController.navigate("loginPage") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = colorResource(R.color.green),
                )
            }
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun TransparentGenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    icon: ImageVector
) {
    val genderOptions = listOf("Male", "Female")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp)
                    .clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        if (selectedGender == "Female") R.drawable.girl
                        else if (selectedGender == "Male") R.drawable.boy
                        else R.drawable.gender
                    ),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Box(modifier = Modifier.weight(1f)) {
                    Text(
                        text = selectedGender.ifEmpty { "Select Gender" },
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    tint = Color.Gray
                )
            }

            Divider()
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(.85f)
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender, color = Color.Gray) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ChangeLanguage() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val englishText = stringResource(R.string.english)
    val hindiText = stringResource(R.string.hindi)
    var selectedLanguage by remember { mutableStateOf(englishText) }
    var languageKey by remember { mutableStateOf(0) }  // Key to trigger recomposition

    Box(modifier = Modifier.clickable { expanded = true }) {
        Text(text = "Change Language")
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(englishText) },
                onClick = {
                    setAppLocale(context, "en")
                    selectedLanguage = englishText
                    languageKey++  // Trigger recomposition
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(hindiText) },
                onClick = {
                    setAppLocale(context, "hi")
                    selectedLanguage = hindiText
                    languageKey++  // Trigger recomposition
                    expanded = false
                }
            )
        }
    }
}

