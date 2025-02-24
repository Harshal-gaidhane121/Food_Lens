package com.example.foodlens.screens

import android.annotation.SuppressLint
import android.graphics.pdf.models.ListItem
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.foodlens.R
import com.example.foodlens.UserViewModel
import com.example.foodlens.getCategoryList
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun CategoriesPage(navHostController: NavHostController,viewModel: UserViewModel) {

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

            Column(modifier = Modifier.fillMaxSize()) {

                TopAppBar(navHostController,"Products",)

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    item{
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(category.chunked(2)) { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowItems.forEach { (imageRes, title) ->
                                CategoryItem(navHostController,cat = title, drawable = imageRes)
                            }
                            // If the last row has only one item, add an empty spacer
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }

                    }
                    item{
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                }
            }

        }




@Composable
fun TopAppBar(navHostController: NavHostController,title:String){

    Card (
        modifier = Modifier.padding(top=40.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(colorResource(R.color.lightGreen)),
        elevation = CardDefaults.cardElevation(10.dp)
    )
    {
        Row(
            modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(.7f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){

            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button")
            }
            Text(text=title,
                color = Color(54, 54, 54, 191),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal)
            )
        }

    }

}

@Composable
fun CategoryItem(navController: NavController, cat: String, drawable: Int) {
    val currentRoute = CurrentRoute(navController)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.clickable {
                navController.navigate("analysisPage")
            }
                .size(160.dp)
                .padding(top =20.dp),
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

