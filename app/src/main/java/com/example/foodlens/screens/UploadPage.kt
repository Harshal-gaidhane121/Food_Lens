package com.example.foodlens.screens

import ChatBotScreen
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils.TruncateAt
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.foodlens.FloatingBottomNavigation
import com.example.foodlens.R
import com.example.foodlens.UserViewModel
import java.io.File

@Composable
fun UploadScreen(navHostController: NavHostController, viewModel: UserViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Heading()

        ImageField()

//        CameraScreen()
        UploadImageType()

        FloatingBottomNavigation(navHostController)
    }
}

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) } // Bitmap to store image

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                capturedImageUri?.let { uri ->
                    try {
                        // Load the full-resolution image from the URI
                        imageBitmap =
                            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        contentAlignment = Alignment.Center
    ) {
        // Open Camera Button
        Button(onClick = {
            val file = File(context.cacheDir, "captured_image.jpg")
            capturedImageUri =
                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

            // Launch the camera intent with the file URI
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri)
            }
            launcher.launch(intent)
        }) {
            Text("Open Camera")
        }

        // Display the captured image, if any
        imageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(250.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun Heading() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(modifier = Modifier.padding(50.dp)) {
            Text(
                text = "Upload & Analyze",
                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Normal),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Just in a click")
                Image(
                    painter = painterResource(R.drawable.camerafortitle),
                    contentDescription = "camera",
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }

    }
}

@Composable
fun ImageField() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 130.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier.size(300.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            colors = CardDefaults.cardColors(Color(226, 245, 255, 255))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Centers the content inside the Box
            ) {
                Text(text = "No Image")
            }

        }


    }
}

@Preview(showSystemUi = true)
@Composable
fun UploadImageType() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 150.dp),

        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(
                modifier = Modifier.padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {

                        },
                    colors = CardDefaults.cardColors(Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(R.drawable.camera1),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentDescription = "Take photo"
                    )
                }
                Text(text = "Take Photo")
            }

            Column(
                modifier = Modifier.padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {

                        },
                    colors = CardDefaults.cardColors(Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(R.drawable.photo),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentDescription = "gallery"
                    )
                }
                Text(text = "Choose From Gallery")
            }


        }


    }

}
