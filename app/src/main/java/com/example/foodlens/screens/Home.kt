package com.example.foodlens.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodlens.FloatingBottomNavigation
import com.example.foodlens.R
import com.example.foodlens.ui.theme.TopBar
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun Home(navHostController: NavHostController) {
    val cardItems = listOf("Card 1", "Card 2", "Card 3", "Card 4", "Card 5", "Card 6")

    Box(modifier = Modifier.fillMaxSize().padding(top = 40.dp)){

        Image(
            painter = painterResource(R.drawable.background2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

//        TopBar()

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()){

            item{

                Text(text="HEALTH TIPS AND ARTICLES",
                    modifier = Modifier.padding(20.dp),
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal)
                )

                Text(text="Healthy Food Choice",
                    modifier = Modifier.padding(),
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal)
                )

                Text(text="HOW TO JUMP ON A HEALTHY BANDWAGON WITH EASE",
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                )

            }



        }


        FloatingBottomNavigation(navHostController)


    }




}


//
//@Composable
//fun CardWithImageAndText() {
//    val items = listOf(
//        Pair(R.drawable.beverages, "Beverages"),
//        Pair(R.drawable.biscuits, "Biscuits"),
//        Pair(R.drawable.breads, "Breads"),
//        Pair(R.drawable.cereals, "Cereals"),
//        Pair(R.drawable.chocolates, "Chocolates"),
//        Pair(R.drawable.dairyproduct, "Dairy Product"),
//        Pair(R.drawable.drinks, "Drinks"),
//        Pair(R.drawable.icecream, "Ice Creams"),
//        Pair(R.drawable.noodles, "Noodles"),
//        Pair(R.drawable.nutbars, "Cereals"),
//        Pair(R.drawable.chocolates, "Nut bars"),
//        Pair(R.drawable.oilandghee, "Oil And Ghee")
//    )
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        // Group items in rows of 3
//        items(items.chunked(3)) { rowItems ->
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                rowItems.forEach { (imageRes, title) ->
//                    Card(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(8.dp),
//                        shape = RoundedCornerShape(12.dp),
//                        colors = CardDefaults.cardColors(Color.White),
//                        elevation = CardDefaults.elevatedCardElevation(4.dp)
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Image(
//                                painter = painterResource(id = imageRes),
//                                contentDescription = title,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(100.dp)
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = title,
//                                style = MaterialTheme.typography.bodyLarge,
//                                modifier = Modifier.padding(8.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
