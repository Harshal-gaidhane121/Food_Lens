package com.example.foodlens.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodlens.R
import com.example.foodlens.UserViewModel


@Composable
fun LoginPage(navHostController: NavHostController ,userViewModel: UserViewModel = viewModel()) {
    var mobileNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

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

        Spacer(modifier = Modifier.height(160.dp))

        Text(
            text = "Welcome Back",
            color = Color(70, 66, 66, 193),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
        )

        Text(
            text = "Sign in to continue",
            color = Color(70, 66, 66, 79),
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(40.dp))

        TransparentTextField(
            value = mobileNo,
            onValueChange = { mobileNo = it },
            placeholder = "Mobile No.",
            isNumberKeyboard = true,
            icon = Icons.Default.AccountBox

        )

        TransparentTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            icon = Icons.Default.Lock
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(.8f)
                .background(colorResource(R.color.green), CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = {
                if(mobileNo.isEmpty() || password.isEmpty() ){
                    Toast.makeText(context, "Incomplete credentials", Toast.LENGTH_SHORT).show()
                }
                userViewModel.loginUser(mobileNo, password) { isValid ->
                    if (isValid) {
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        navHostController.navigate("home") {
                            popUpTo(0) // Removes login from back stack
                            launchSingleTop = true
                        }

                    } else {
                        Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
                //TODO CHECK CREDENTIALS FROM DATABASE AND IF CORRECT
            }) {
            Text(
                text = "Login",
                fontSize = 19.sp,
                color = Color.White
                )
        }

        TextButton(onClick = {

        }) {
            Text(text="Forget Password?",
                color = colorResource(R.color.green)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text="Don't have an account yet?", color = Color(1, 1, 1, 122))

            TextButton(
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    //REGISTER into DATABASE
                    navHostController.navigate("register"){
                        popUpTo("loginPage"){
                            inclusive=true
                        }
                    }
                }
            ) {
                Text(
                    text="Register",
                    color = colorResource(R.color.green),
                )
            }
        }

    }
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
    var passwordVisible by remember { mutableStateOf(false) }
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
            textStyle = TextStyle(
                color = Color.Gray,
                fontSize = 16.sp
            ), keyboardOptions = KeyboardOptions(
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

                        Spacer(modifier = Modifier.size(8.dp)) // Space between icon and text

                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
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
