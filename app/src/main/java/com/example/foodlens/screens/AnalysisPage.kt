package com.example.foodlens.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodlens.R
import kotlinx.coroutines.launch
import kotlin.math.min


@Composable
fun AnalysisPage(name: String, value: Float, navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(navHostController, "Analysis")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .size(350.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(5.dp)
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.snickers),
                            contentDescription = null,
                            modifier = Modifier.scale(1.2f)
                        )

                        Text(
                            text = name,
                            modifier = Modifier.padding(top = 180.dp),
                            color = Color(54, 54, 54, 191),
                            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
                        )

                        MeterArc(value, modifier = Modifier.scale(1.1f))
                        AboutColor()

                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
                WhatIsItUpTo("What Concerns Us", R.drawable.shocked)
                NutritionItem("Energy", 2f, "Energy levels are high which can cause weight gain ")
                NutritionItem("Sugar", 2f, "Energy levels are high which can cause weight gain ")
                NutritionItem("Fat", 2f, "Energy levels are high which can cause weight gain ")

                WhatIsItUpTo("Neutral", R.drawable.neutral)
                NutritionItem("Energy", 5f, "Energy levels are high which can cause weight gain ")
                NutritionItem("Energy", 5f, "Energy levels are high which can cause weight gain ")
                NutritionItem("Energy", 5f, "Energy levels are high which can cause weight gain ")

                WhatIsItUpTo("What We Like", R.drawable.smile)
                NutritionItem("Protein", 8f, "Energy levels are high which can cause weight gain ")
                NutritionItem("Fiber", 8f, "Energy levels are high which can cause weight gain ")
                NutritionItem(
                    "Carbohydrates",
                    8f,
                    "Energy levels are high which can cause weight gain "
                )

            }
            item {
                SuggestionsInAnalysis()
            }
            item {
                Conclusion(
                    "Conclusion",
                    "Energy levels are high which can cause weight gainEnergy levels are high which can cause weight gainEnergy levels are high which can cause weight gainEnergy levels are high which can cause weight gainEnergy levels are high which can cause weight gainEnergy levels are high which can cause weight gain"
                )
            }
        }
    }
}


@Composable
fun MeterArc(value: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val sweepAngle = (252f / 5) * value // 70% of 360° scaled to 5 max value

        val color = when {
            value < 1.7 -> colorResource(R.color.red)
            value in 1.7f..2.8f -> colorResource(R.color.orange)
            value in 2.9f..3.7f -> colorResource(R.color.yellow)
            else -> colorResource(R.color.green)
        }

        Canvas(modifier = modifier.size(400.dp)) {
            val strokeWidth = size.minDimension * 0.07f
            val radius = min(size.width, size.height) / 2 - strokeWidth

            drawArc(
                color = Color(209, 231, 223, 237),
                startAngle = -216f, // Start at -216° (left-top)
                sweepAngle = 252f, // Full arc for reference
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset((size.width - radius * 2) / 2, (size.height - radius * 2) / 2)
            )

            drawArc(
                color = color,
                startAngle = -216f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset((size.width - radius * 2) / 2, (size.height - radius * 2) / 2)
            )
        }
    }

}


@Composable
fun AboutColor() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            AboutColorItem("Healthy", colorResource(R.color.green))
            AboutColorItem("Neutral", colorResource(R.color.yellow))
            AboutColorItem("Unhealthy", colorResource(R.color.orange))
            AboutColorItem("Harmfull", colorResource(R.color.red))


        }
    }

}

@Composable
fun AboutColorItem(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Canvas(modifier = Modifier.size(15.dp)) {
            drawCircle(color = color, radius = size.minDimension / 2)
        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = text,
            fontSize = 15.sp
        )

    }

}

@Composable
fun RatingMeter(
    rating: Float = 7f // Value from 0 to 10
) {
    val animatedRating by animateFloatAsState(targetValue = min(rating, 10f), label = "")

    val colors = listOf(
        Color(0xFFD32F2F), // Red
        Color(0xFFFF8C00), // Orange
        Color(0xFFFFC20E), // Yellow
        Color(0xFF7AC943), // Light Green
    )

    val sectionCount = colors.size
    val sectionWidth = 60.dp // Adjust width of each section
    val spacing = 4.dp // Gap between sections

    Column(modifier = Modifier.fillMaxWidth()) {
        // Meter
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(213, 210, 210, 164))
                .padding(5.dp)
                .fillMaxWidth()

        ) {
            colors.forEachIndexed { index, color ->
                if (index == 0) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(15.dp)
                            .padding(horizontal = 1.dp)
                            .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                            .background(color)
                            .padding(horizontal = if (index < sectionCount - 1) spacing else 0.dp)
                    )
                } else if (index == 3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 1.dp)
                            .height(15.dp)
                            .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                            .background(color)
                            .padding(horizontal = if (index < sectionCount - 1) spacing else 0.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 1.dp)
                            .height(15.dp)
                            .background(color)
                            .padding(horizontal = if (index < sectionCount - 1) spacing else 0.dp)
                    )
                }

            }
        }

        // Pointer
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        ) {
            val totalWidth = size.width
            val position = (animatedRating / 10f) * totalWidth

            // Draw pointer triangle
            val pointerSize = 40f
            val path = Path().apply {
                moveTo(position, 0f)
                lineTo(position - pointerSize / 2, pointerSize)
                lineTo(position + pointerSize / 2, pointerSize)
                close()
            }

            drawIntoCanvas {
                it.drawPath(path, Paint())
            }
        }
    }
}

@Composable
fun NutritionItem(item: String, rating: Float, description: String) {

    val colors = listOf(
        Color(0xFFD32F2F), // Red
        Color(0xFFFFC20E), // Yellow
        Color(0xFF7AC943), // Light Green
    )
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = if (expanded) 10.dp else 0.dp)
            .fillMaxWidth()
            .wrapContentSize(),
        colors = CardDefaults.cardColors(Color(236, 235, 235, 75))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = item,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = if (rating <= 4) colors[0]
                    else if (rating > 4 && rating <= 7) colors[1]
                    else colors[2],
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                IconButton(onClick = { expanded = !expanded }) {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    RatingMeter(rating)
                    Text(text = description, color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun WhatIsItUpTo(title: String, emoji: Int) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
        Spacer(modifier = Modifier.size(7.dp))
        Image(painter = painterResource(emoji), contentDescription = null)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Conclusion(item: String, description: String) {
    var expanded by remember { mutableStateOf(false) }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = if (expanded) 10.dp else 20.dp)
            .fillMaxWidth()
            .wrapContentSize()
            .onGloballyPositioned {
                if (expanded) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        colors = CardDefaults.cardColors(Color(236, 235, 235, 128))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                        if (expanded) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = item,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 23.sp
                )

                IconButton(onClick = {
                    expanded = !expanded
                    if (expanded) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }) {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    Text(
                        text = description,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionsInAnalysis() {
    val list = listOf(
        SuggestedItems("Banana", "Banana is considered healthy depending on your health issues"),
        SuggestedItems("Mango", "Mango is considered healthy depending on your health issues"),
        SuggestedItems("Apple", "Apple is considered healthy depending on your health issues"),
        SuggestedItems("Pineapple", "Pineapple is considered healthy depending on your health issues")
    )

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 20.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column {
            Text(
                text = "Suggested Food Items",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp)
            )

            LazyRow(
                modifier = Modifier.padding()
            ) {
                items(list) { item ->
                    SuggestedFoodCard(item)
                }
            }

        }
    }
}

@Composable
fun SuggestedFoodCard(item: SuggestedItems) {
    Card(
        modifier = Modifier.width(300.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = item.description, fontSize = 12.sp)
        }
    }
}

data class SuggestedItems(val name: String, val description: String)
