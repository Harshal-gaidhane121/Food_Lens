package com.example.foodlens.screens

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.provider.CalendarContract.Colors
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodlens.FloatingBottomNavigation
import com.example.foodlens.R
import com.example.foodlens.UserViewModel
import com.example.foodlens.ui.theme.TopBar
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Locale.Category


@Composable
fun Home(navHostController: NavHostController,viewModel: UserViewModel) {


    val context = LocalContext.current



    val category = listOf(
        Pair(R.drawable.beverages, "Beverages"),
        Pair(R.drawable.biscuits, "Biscuits"),
        Pair(R.drawable.breads, "Breads"),
        Pair(R.drawable.cereals, "Cereals"),
        Pair(R.drawable.chocolates, "Chocolates"),
        Pair(R.drawable.dairyproduct, "Dairy Product"),
        Pair(R.drawable.drinks, "Drinks"),
        Pair(R.drawable.icecream, "Ice Creams"),
        Pair(R.drawable.noodles, "Noodles"),
        Pair(R.drawable.nutbars, "Cereals"),
        Pair(R.drawable.chocolates, "Nut bars"),
        Pair(R.drawable.oilandghee, "Oil And Ghee"),
        Pair(R.drawable.snacks, "Snacks"),
        Pair(R.drawable.spices, "Spices"),
        Pair(R.drawable.toast, "Toast"),
        Pair(R.drawable.vegetables, "Vegetables"),
        Pair(R.drawable.wheat, "Wheat"),
        Pair(R.drawable.wheat, "Wheat"),


        )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {


        Image(
            painter = painterResource(R.drawable.plainbackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

//        TopBar()

//        Card(modifier = Modifier.fillMaxSize(),
//            colors = CardDefaults.cardColors(colorResource(R.color.lightGreen))){
//
//        }


        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            item{
                Profile()
            }

            item {
                Text(
                    text = "HEALTH TIPS AND ARTICLES",
                    modifier = Modifier.padding(20.dp),
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal)
                )
            }

            item {
                Carousel()
            }

            item {
                Text(
                    text = "Healthy Food Choice",
                    modifier = Modifier.padding(),
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal)
                )

                Text(
                    text = "HOW TO JUMP ON A HEALTHY BANDWAGON WITH EASE",
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                )

            }
            items(category.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { (imageRes, title) ->

                        HomeCategoryItem(viewModel,navHostController,cat = title, drawable = imageRes)
                    }
                    // If the last row has only one item, add an empty spacer
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

            }
            item {
                Spacer(modifier = Modifier.height(90.dp))
            }

        }

        FloatingBottomNavigation(navHostController)

        ExitDialogBox(context)

    }


}


@Composable
fun ExitDialogBox(context: Context) {

    val activity = rememberUpdatedState(newValue = context as? Activity)

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }


    if (showExitDialog) {
        AlertDialog(
            containerColor =Color.White,
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App", color = Color(1,1,1)) },
            text = { Text("Are you sure you want to exit?", color = Color(1,1,1)) },
            confirmButton = {

                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.lightGreen)),
                    onClick = {
                    showExitDialog = false

                    (context as? Activity)?.finish()

//                    activity.value?.finish()    Exit app
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {

                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.lightGreen)),
                    onClick = { showExitDialog = false }) {
                    Text("No")
                }
            }
        )
    }
    
}


@Composable
fun HomeCategoryItem(viewModel: UserViewModel,navController: NavController, cat: String, drawable: Int) {
    val currentRoute = CurrentRoute(navController)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.clickable {

                viewModel.setCategory(cat)
                navController.navigate("loadingPage")

            }
                .size(130.dp)
                .padding(start = 15.dp, end = 10.dp, top = 20.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = drawable), contentDescription = cat,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Text(text = cat)
    }


}

@Composable
fun Carousel() {

    val images = listOf(
        R.drawable.snacks,
        R.drawable.snacks,
        R.drawable.snacks,
        R.drawable.snacks
    )

    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L) // Auto-scroll every 2 seconds
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Carousel Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }

}



@Composable
fun Profile() {

    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.lightGreen)),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(.7f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(color = colorResource(R.color.white))
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = colorResource(R.color.green)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Hi, Harshal",
                    color = Color(70, 66, 66, 193),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal),

                    ) // TODO REPLACE THIS WITH USERS NAME

            }
        }


    }
}

@Composable
fun CurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


@Composable
fun LoadingScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000)  // Wait for 2 seconds

        navController.navigate("categoriesPage") {
            popUpTo("loadingPage") { inclusive = true } // Remove loading screen from back stack
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackHandler {
            navController.navigate("home")
        }
        CircularProgressIndicator()
    }
}
