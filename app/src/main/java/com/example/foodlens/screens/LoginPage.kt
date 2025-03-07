

package com.example.foodlens.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodlens.LocalLanguageManager
import com.example.foodlens.R
import com.example.foodlens.networks.LoginRequest
import com.example.foodlens.network.RetrofitClient // Use RetrofitClient for login
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun LoginPage(navHostController: NavHostController) {
    val languageManager = LocalLanguageManager.current
    val currentLanguage = languageManager.currentLanguage

    var mobile by remember { mutableStateOf("") } // Changed from email to mobile
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val apiService = RetrofitClient.getApiService(context) // Use RetrofitClient with context

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
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = stringResource(R.string.login),
            color = Color(70, 66, 66, 193),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
        )

        Spacer(modifier = Modifier.height(40.dp))

        TransparentTextField(
            value = mobile,
            onValueChange = { mobile = it },
            placeholder = stringResource(R.string.mobile_no), // Updated to mobile_no
            icon = Icons.Default.Phone,
            isNumberKeyboard = true // Added for mobile input
        )

        TransparentTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            icon = Icons.Default.Lock
        )

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(.8f)
                .background(colorResource(R.color.green), CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = {
                if (mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, context.getString(R.string.incomplete_credentials), Toast.LENGTH_SHORT).show()
                } else if (mobile.length < 10) {
                    Toast.makeText(context, context.getString(R.string.invalid_mobile_number), Toast.LENGTH_SHORT).show()
                } else {
                    coroutineScope.launch {
                        try {
                            val request = LoginRequest(mobile, password)
                            val response = apiService.loginUser(request)

                            if (response.isSuccessful) {
                                response.body()?.let { body ->
                                    if (body.message != null) {
                                        Toast.makeText(context, body.message, Toast.LENGTH_SHORT).show()
                                        // Save token if present
                                        body.token?.let { RetrofitClient.setToken(it) }
                                        navHostController.navigate("home") {
                                            popUpTo("loginPage") { inclusive = true }
                                        }
                                    }
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                val errorMessage = if (!errorBody.isNullOrEmpty()) {
                                    JSONObject(errorBody).getString("message")
                                } else {
                                    response.body()?.error ?: context.getString(R.string.login_failed)
                                }
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            errorMessage = "${context.getString(R.string.error)}: ${e.message}"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        ) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 19.sp,
                color = Color.White
            )
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.no_account),
                color = Color(1, 1, 1, 122)
            )

            TextButton(
                onClick = {
                    navHostController.navigate("register") {
                        popUpTo("loginPage") { inclusive = true }
                    }
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.register),
                    color = colorResource(R.color.green),
                )
            }
        }
    }
}

fun loginUser(
    firestore: FirebaseFirestore,
    context: Context,
    mobileNo: String,
    password: String,
    navHostController: NavHostController,
    onComplete: () -> Unit
) {
    firestore.collection("Users")
        .document(mobileNo)
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val storedPassword = document.getString("password")
                if (storedPassword == password) {
                    saveCurrentUser(context, mobileNo)

                    context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        .edit().putBoolean("isLoggedIn", true).apply()

                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()

                    navHostController.navigate("home") {
                        popUpTo(0) // Clears back stack
                        launchSingleTop = true
                    }
                } else {
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
            }
            onComplete()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Login failed. Try again.", Toast.LENGTH_SHORT).show()
            onComplete()
        }
}

fun saveToken(context: Context, token: String) {
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPref.edit().putString("AUTH_TOKEN", token).apply()
}

fun saveCurrentUser(context: Context, mobileNo: String) {
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPref.edit().putString("LOGGED_IN_USER", mobileNo).apply()
}

@Composable
fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isNumberKeyboard: Boolean = false,
    isPassword: Boolean = false,
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(10.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isNumberKeyboard) KeyboardType.Number else KeyboardType.Text
            ),
            decorationBox = { innerTextField ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text(text = placeholder, color = Color.Gray, fontSize = 16.sp)
                            }
                            innerTextField()
                        }
                    }
                    Divider()
                }
            }
        )
    }
}


