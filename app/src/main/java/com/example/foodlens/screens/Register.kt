package com.example.foodlens.screens


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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.foodlens.User
import com.example.foodlens.UserViewModel
import com.example.foodlens.UserViewModelFactory

@Composable
fun Register(navHostController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    var mobileNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current


    // This will handle the result of registration attempt
    val registrationStatus = remember { mutableStateOf(false) }

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
            text = "Register",
            color = Color(70, 66, 66, 193),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Normal),
        )

        Spacer(modifier = Modifier.height(40.dp))

        TransparentTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = "Name",
            icon = Icons.Default.Person
        )

        TransparentTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email",
            icon = Icons.Default.Email
        )

        TransparentTextField(
            value = mobileNo,
            onValueChange = { mobileNo = it },
            placeholder = "Mobile No.",
            isNumberKeyboard = true,
            icon = Icons.Default.Phone
        )

        TransparentTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            icon = Icons.Default.Lock
        )

        Spacer(modifier = Modifier.height(80.dp))

        // Register button
        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(.8f)
                .background(colorResource(R.color.green), CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = {
                if (mobileNo.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(context, "Incomplete credentials", Toast.LENGTH_SHORT).show()
                }
                else if (mobileNo.length < 10) {
                    Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_SHORT).show()
                }else {
                    // Check if mobile number already exists
                    userViewModel.isMobileRegistered(mobileNo) { exists ->
                        if (exists) {
                            Toast.makeText(context, "Mobile number already exists!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Register user in database
                            val user = User(
                                name = name,
                                email = email,
                                mobile = mobileNo,
                                password = password
                            )
                            userViewModel.registerUser(user) {
                                registrationStatus.value = it
                                if (it) {
                                    navHostController.navigate("loginPage"){
                                        popUpTo("loginPage"){
                                            inclusive=true
                                        }
                                    }
                                } else {
                                    errorMessage = "Registration failed"
                                }
                            }
                            Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        ) {
            Text(
                text = "Register",
                fontSize = 19.sp,
                color = Color.White
            )
        }

        // Error message display
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", color = Color(1, 1, 1, 122))

            TextButton(
                onClick = { navHostController.navigate("loginPage"){
                    popUpTo("register"){
                        inclusive=true
                    }
                } },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = "Login",
                    color = colorResource(R.color.green),
                )
            }
        }
    }
}

