package com.example.stockanalyst.TicketScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockanalyst.Model.ProductModel

@Composable
fun miniGraph(model: ProductModel, nav: NavController) {

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .border(5.dp, Color.Black)
        .pointerInput(key1 = null,){
            detectTapGestures(onTap = { Offset ->
                offsetX = Offset.x
                offsetY = Offset.y})
        })
    {

//        drawLine(color = Color.Blue,start = Offset(offsetX,0f), end = Offset(offsetX,size.height))
//        drawLine(color = Color.Blue,start = Offset(0f,offsetY), end = Offset(size.width,offsetY))
        drawCoordiantion(drawScope = this)

    }
}

fun drawCoordiantion(drawScope: DrawScope){
    var s = drawScope.size

    for (a  in s.width.toInt() downTo 0 step 70){
        drawScope.drawLine(color = Color(0xabb2b9),start = Offset(a.toFloat(),0f), end = Offset(a.toFloat(),s.height))
    }
    for (a  in s.height.toInt() downTo 0 step 70){
        drawScope.drawLine(color = Color(0xabb2b9),start = Offset(0f, a.toFloat()), end = Offset(s.width,
            a.toFloat()
        ))
    }
}


