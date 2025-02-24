package com.example.foodlens.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
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
                    .fillMaxWidth().
                    padding(20.dp)
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
                NutritionRow()
            }

            item{
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .size(600.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(10.dp)
                )
                {
                    NutritionTable()
                }

                Card(modifier = Modifier.
                      padding(20.dp)
                    .fillMaxWidth()
                    .wrapContentSize(),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(10.dp),
                )
                {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)) {

                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text="Conclusion",
                                color = Color(54, 54, 54, 191),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal))
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(modifier = Modifier.fillMaxSize()){
                            Text(
                                text="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                                textAlign = TextAlign.Justify,
                                color = Color(54, 54, 54, 191),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                                )
                        }

                    }
                }

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
fun NutritionTable() {

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly){

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text="Nutrition Table",
                color = Color(54, 54, 54, 191),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal))
        }

        NutritionItems("Protein",4.2f)
        NutritionItems("Energy",4f)
        NutritionItems("Fat",2.4f)
        NutritionItems("Sugar",1.7f)
        NutritionItems("Cholesterol",2f)
        NutritionItems("Saturated fat",1f)
        NutritionItems("Carbohydrates",3f)
        NutritionItems("Fiber",3.3f)
        NutritionItems("Salt",3.3f)
        NutritionItems("Fruits, vegetable, nuts and rapeseed, walnut and olive oil",3f)


    }

}

@Composable
fun NutritionItems(name: String, value: Float,) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically)
    {
        Column() {

            Row(){
                Text(text = name,
                    color = Color(54, 54, 54, 191),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal))
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(){
                val progress = value / 5f // Normalize value (0-5) → (0-1)

                val color = when {
                    value < 2 ->colorResource(R.color.red)
                    value in 2f..3.9f -> colorResource(R.color.yellow)
                    else -> colorResource(R.color.green)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth() // 70% width
                        .height(10.dp) // Thickness
                        .background(colorResource(R.color.lightGrey), RoundedCornerShape(10.dp)) // Background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress) // Fill based on value
                            .height(10.dp)
                            .background(color, RoundedCornerShape(10.dp)) // Foreground
                    )
                }
            }
        }
    }
}

@Composable
fun NutritionRow() {

    LazyRow(modifier = Modifier.fillMaxWidth()) {

        item{
            NutritionRowItem("Protein",4.2f)
            NutritionRowItem("Energy",4f)
            NutritionRowItem("Fat",3.7f)
            NutritionRowItem("Sugar",3.6f)
            NutritionRowItem("Cholesterol",2f)
            NutritionRowItem("Saturated fat",1f)
            NutritionRowItem("Carbohydrates",3.6f)
            NutritionRowItem("Fiber",4f)
            NutritionRowItem("Salt",3.3f)
        }

    }
}




@Composable
fun NutritionRowItem( name :String,value: Float,) {

    val color = when {
        value < 2 -> colorResource(R.color.red)
        value in 2f..3.9f -> colorResource(R.color.yellow)
        else -> colorResource(R.color.green)
    }
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(color),
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(text=name,color=Color.White)
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