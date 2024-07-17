package com.example.stockanalyst.TicketScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.colorLight
import com.example.stockanalyst.Graph
import com.example.stockanalyst.Model.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun roundDouble(num:Double): Double{

    val number3digits:Double = Math.round(num * 1000.0) / 1000.0
    val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
    val solution:Double = Math.round(number2digits * 10.0) / 10.0
    return number3digits
}

@Composable
fun Float.toDp() = with(LocalDensity.current) {this@toDp.toDp()}

@Composable
fun miniGraph(model: ProductModel, nav: NavController, selectedTicket: String) {
    var listInterval = listOf<String>("1D","1W","2W","1MO","3MO","3Y")
    var selected by remember { mutableStateOf(listInterval[0]) }
    var date by remember { mutableStateOf(emptyList<Int>()) }
    var price by remember { mutableStateOf(emptyList<Double>()) }
    var indexPrice by remember { mutableStateOf(0) }
    var offsetX by remember { mutableStateOf(0f) }

   LaunchedEffect(key1 = selected){
       var data = when (selected) {
           "1D" -> model.getGraphData(selectedTicket, "1d", "5m")
           "1W" -> model.getGraphData(selectedTicket, "5d", "5m")
           "2W" -> model.getGraphData(selectedTicket, "10d", "15m")
           "1MO" -> model.getGraphData(selectedTicket, "1mo", "30m")
           "3MO" -> model.getGraphData(selectedTicket, "3mo", "1d")
           "3Y" -> model.getGraphData(selectedTicket, "3y", "1d")
           else -> {
               model.getGraphData(selectedTicket, "5d", "15m")
           }}

       date = data.chart.result[0].timestamp
       price = data.chart.result[0].indicators.quote[0].close
   }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .background(color = colorLight.outlineLight)) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .border(2.dp, Color.Black)
            .pointerInput(key1 = null,) {
                detectHorizontalDragGestures(onDragStart = { offset -> offsetX = offset.x },
                    onDragEnd = { offsetX = 0f },
                    onHorizontalDrag = { _, dragAmount -> offsetX += dragAmount })
            })
        {

            if (!date.isEmpty() && !price.isEmpty()) {
//            if (corutine.coroutineContext.job.isCompleted){
                var minYPoint = price.min() - .5f
                var maxYPoint = price.max() + .5f
                var x = roundDouble((size.width / date.size).toDouble())
                var y_axist = price.map { item -> (size.height - (((item - minYPoint).toFloat() / (maxYPoint - minYPoint).toFloat()) * size.height)).toInt() }
                drawCoordiantion(this)
                drawGraph(this, model, x.toFloat(), y_axist)
//                if (roundDouble(offsetX.toDouble())%x ==0.0) {
                    indexPrice = (roundDouble(offsetX.toDouble())/x).toInt()
//                }
            }

            drawLine(Color.White, Offset(offsetX,0f), Offset(offsetX,size.height), strokeWidth = 4f)
//        }

        }
        Box(modifier = Modifier
            .padding(5.dp)
            .align(Alignment.BottomStart)
            ){
            Row {
                listInterval.forEach {
                    item -> intervalItem(item,selected) { selected = it }
                }
            }
        }
        Box(modifier = Modifier
            .padding(5.dp)
            .offset(x = offsetX.toDp(), y = 150.dp)
            .height(50.dp)
            .width(70.dp)
            .align(Alignment.TopStart)){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                if (price.isNotEmpty()) {
                    Text(text = "${roundDouble(price[indexPrice].toDouble())}" , fontSize = 15.sp, color = Color.White, modifier = Modifier.background(colorLight.onSurfaceVariantLight))
//                Text(text = "${offsetY}")
                }
            }
        }

        FloatingActionButton(onClick = { nav.navigate(Graph.route) }, modifier = Modifier.align(
            Alignment.BottomEnd
        )) {
            Icon(Icons.Default.Menu , contentDescription ="Expand graph" )
        }
        }
    }


@Composable
fun intervalItem(item: String, selected: String, onSelected: (String) -> Unit) {
    var color:Color = if (selected.equals(item)) colorLight.tertiaryLight else colorLight.secondaryLight
    Text(text = item, color = Color.White, fontSize = 20.sp, modifier = Modifier
        .padding(5.dp)
        .border(
            .5.dp,
            Color.Black, shape = RectangleShape
        )
        .clickable { onSelected(item) }
        .background(color))
}

fun drawGraph(
    drawScope: DrawScope,
    model: ProductModel,
    date: Float,
    price: List<Int>,
    ) {
    var color:Color = if (price.get(0) >price.get(price.lastIndex)) Color.Green else Color.Red
    var x = 0f
    var path = Path()
    path.moveTo(x, price[0].toFloat())
    for (y in price){
        path.lineTo(x,y.toFloat())
        x+=date
    }
    println{"price size:${price.size}"}
    drawScope.drawPath(path = path, color ,style = Stroke(width = 10f))

}


fun drawCoordiantion(drawScope: DrawScope){
    var s = drawScope.size

    for (a  in s.width.toInt()+1000 downTo 0 step 70){
        drawScope.drawLine(color = Color.LightGray,start = Offset(a.toFloat(),0f), end = Offset(a.toFloat(),s.height))
    }
    for (a  in s.height.toInt()+1000 downTo 0 step 70){
        drawScope.drawLine(color = Color.LightGray,start = Offset(0f, a.toFloat()), end = Offset(s.width,
            a.toFloat()
        ))
    }
}


