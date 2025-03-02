package com.example.foodlens.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodlens.R
import kotlin.math.min


@Composable
fun AnalysisPage(name: String, value:Float, navHostController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        TopAppBar(navHostController,"Analysis")

        LazyColumn(modifier = Modifier
            .fillMaxSize()) {
            item {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .size(350.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(10.dp)
                )
                {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center)
                    {
                        Image(painter = painterResource(R.drawable.snickers),
                            contentDescription = null,
                            modifier = Modifier.scale(1.2f))

                        Text(text = name,
                            modifier = Modifier.padding(top=180.dp),
                            color = Color(54, 54, 54, 191),
                            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
                        )

                        MeterArc(value, modifier = Modifier.scale(1.1f))
                        AboutColor()

                    }
                }
            }

            item{
                    NutritionItem("Energy",8f)

            }
        }
    }
}


@Composable
fun MeterArc(value: Float, modifier: Modifier = Modifier) {
    Box(modifier=Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        val sweepAngle = (252f / 5) * value // 70% of 360° scaled to 5 max value

        val color = when {
            value < 2 -> colorResource(R.color.red)
            value in 2f..3.5f -> colorResource(R.color.yellow)
            else ->colorResource(R.color.green)
        }

        Canvas(modifier = modifier.size(400.dp)) {
            val strokeWidth = size.minDimension * 0.07f
            val radius = min(size.width, size.height) / 2 - strokeWidth

            drawArc(
                color =Color(209, 231, 223, 237),
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
        contentAlignment = Alignment.BottomStart)
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically)
        {
            AboutColorItem("Healthy",colorResource(R.color.green))
            AboutColorItem("Neutral", colorResource(R.color.yellow))
            AboutColorItem("Unhealthy",colorResource(R.color.red))

        }
    }

}

@Composable
fun AboutColorItem(text:String,color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically){

        Canvas(modifier = Modifier.size(15.dp)) {
            drawCircle(color = color, radius = size.minDimension / 2)
        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(text=text,
            fontSize = 15.sp)

    }

}

@Composable
fun RatingMeter(
    rating: Float=7f // Value from 0 to 10
) {
    val animatedRating by animateFloatAsState(targetValue = min(rating, 10f), label = "")

    val colors = listOf(
        Color(0xFFD32F2F) , // Red
        Color(0xFFFF8C00), // Orange
        Color(0xFFFFC20E), // Yellow
        Color(0xFF7AC943), // Light Green
    )

    val sectionCount = colors.size
    val sectionWidth = 60.dp // Adjust width of each section
    val spacing = 4.dp // Gap between sections

    Column() {
        // Meter
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(213, 210, 210, 164))
                .padding(5.dp)

        ) {
            colors.forEachIndexed { index, color ->
                if(index==0 ){
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(15.dp)
                            .padding(horizontal = 1.dp)
                            .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                            .background(color)
                            .padding(horizontal = if (index < sectionCount - 1) spacing else 0.dp)
                    )
                }
                else if(index==3){
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 1.dp)
                            .height(15.dp)
                            .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                            .background(color)
                            .padding(horizontal = if (index < sectionCount - 1) spacing else 0.dp)
                    )
                }
                else{
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
fun NutritionItem(item : String, rating: Float) {

    Card(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .size(80.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors =CardDefaults.cardColors(Color.White),

    ) {

        Text(text=item, color = Color.Gray)
        
        RatingMeter(rating)
    }
    
}
//
//@Composable
//fun NutritionTable() {
//
//    Column (modifier = Modifier
//        .fillMaxSize()
//        .padding(horizontal = 20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly){
//
//        Row (modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Text(text="Nutrition Table",
//                color = Color(54, 54, 54, 191),
//                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal))
//        }
//
//        NutritionItems("Protein",4.2f)
//        NutritionItems("Energy",4f)
//        NutritionItems("Fat",2.4f)
//        NutritionItems("Sugar",1.7f)
//        NutritionItems("Cholesterol",2f)
//        NutritionItems("Saturated fat",1f)
//        NutritionItems("Carbohydrates",3f)
//        NutritionItems("Fiber",3.3f)
//        NutritionItems("Salt",3.3f)
//        NutritionItems("Fruits, vegetable, nuts and rapeseed, walnut and olive oil",3f)
//
//
//    }
//
//}

//@Composable
//fun NutritionItems(name: String, value: Float,) {
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.Start,
//        verticalAlignment = Alignment.CenterVertically)
//    {
//        Column() {
//
//            Row(){
//                Text(text = name,
//                    color = Color(54, 54, 54, 191),
//                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal))
//            }
//            Spacer(modifier = Modifier.height(5.dp))
//            Row(){
//                val progress = value / 5f // Normalize value (0-5) → (0-1)
//
//                val color = when {
//                    value < 2 ->colorResource(R.color.red)
//                    value in 2f..3.9f -> colorResource(R.color.yellow)
//                    else -> colorResource(R.color.green)
//                }
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth() // 70% width
//                        .height(10.dp) // Thickness
//                        .background(colorResource(R.color.lightGrey), RoundedCornerShape(10.dp)) // Background
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth(progress) // Fill based on value
//                            .height(10.dp)
//                            .background(color, RoundedCornerShape(10.dp)) // Foreground
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun NutritionRow() {
//
//    LazyRow(modifier = Modifier.fillMaxWidth()) {
//
//        item{
//            NutritionRowItem("Protein",4.2f)
//            NutritionRowItem("Energy",4f)
//            NutritionRowItem("Fat",3.7f)
//            NutritionRowItem("Sugar",3.6f)
//            NutritionRowItem("Cholesterol",2f)
//            NutritionRowItem("Saturated fat",1f)
//            NutritionRowItem("Carbohydrates",3.6f)
//            NutritionRowItem("Fiber",4f)
//            NutritionRowItem("Salt",3.3f)
//        }
//
//    }
//}


//
//
//@Composable
//fun NutritionRowItem( name :String,value: Float,) {
//
//    val color = when {
//        value < 2 -> colorResource(R.color.red)
//        value in 2f..3.9f -> colorResource(R.color.yellow)
//        else -> colorResource(R.color.green)
//    }
//    Button(
//        onClick = {
//
//        },
//        colors = ButtonDefaults.buttonColors(color),
//        modifier = Modifier.padding(horizontal = 10.dp)
//    ) {
//        Text(text=name,color=Color.White)
//    }
//}

