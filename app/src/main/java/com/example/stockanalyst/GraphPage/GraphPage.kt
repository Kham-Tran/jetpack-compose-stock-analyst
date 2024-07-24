package com.example.stockanalyst.GraphPage

import android.health.connect.datatypes.units.Percentage
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose.colorLight
import com.example.stockanalyst.Model.ProductModel
import com.example.stockanalyst.TicketScreen.toDp
import java.text.SimpleDateFormat
import java.util.Locale

private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
fun timeStampToDate(timestamp:Int):String{
    return simpleDateFormat.format(timestamp*1000L)
}



@Composable
fun GraphPage(Nav: NavHostController, model: ProductModel, selectedTicket: String) {
    var listRange = listOf<String>("1W","2W","3MO","6MO","3Y")
    var color = listOf<Color>(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta)
    var listGraph by rememberSaveable {
        mutableStateOf(listOf(selectedTicket))
    }
//    var listChange = mutableListOf<Double>(0.0)
    var listPercent by rememberSaveable {
        mutableStateOf(mutableListOf(0.0))
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    var keyword by remember {
        mutableStateOf("")
    }
    var range by rememberSaveable {
        mutableStateOf(listRange[0])
    }

    var scale by rememberSaveable {
        mutableStateOf(1f)
    }

    var analysisMode by rememberSaveable {
        mutableStateOf(false)
    }

    var left by rememberSaveable {
        mutableStateOf(0f)
    }
    var right by rememberSaveable {
        mutableStateOf(0f)
    }
    var dateleft by rememberSaveable {
        mutableStateOf(0)
    }
    var dateright by rememberSaveable {
        mutableStateOf(0)
    }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }
    var state = rememberTransformableState(onTransformation = {zoomChange, panChange, rotationChange ->
        scale*=zoomChange
        rotation += rotationChange
        offset += panChange})


    Box(modifier = Modifier
        .fillMaxSize()
        .transformable(state = state)
        .background(color = colorLight.outlineLight)
        .pointerInput(null) {
            detectTapGestures(onLongPress = { offset -> analysisMode = !analysisMode })
        }
//        .pointerInput(null){
//            detectTransformGestures(
//                panZoomLock = false,
//                onGesture = {centroid, pan, zoom, rotation ->
//                    if(scale>=1f){
//                        scale*=zoom
//                    }
//                    else{
//                        scale=1f
//                    }
//                }
//            )
//        }
    ) {

        Canvas(modifier = Modifier.fillMaxSize()){
            left =(size.width/4)
            right=((size.width/4)*3)
        }

        AnimatedVisibility(visible = analysisMode, modifier = Modifier) {
            Box(modifier=Modifier.fillMaxSize()) {
                Column(modifier=Modifier.align(Alignment.TopCenter)){
                    listPercent.forEachIndexed { index, d ->
                        Text(text = "${listGraph[index]} : ${roundDouble(d)}% ")
                    }
                }
                Text(text = "${timeStampToDate(dateleft)} ---- ${timeStampToDate(dateright)}", modifier = Modifier
                    .align(Alignment.BottomCenter))

                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .width(60.dp)
                        .drawBehind {
                            drawLine(
                                Color.White,
                                Offset(left, 0f),
                                Offset(left, size.height),
                                strokeWidth = 4f
                            )

                        }
                        .offset(left.toDp() - 20.dp, 0.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .pointerInput(key1 = null,) {
                            detectHorizontalDragGestures(onDragStart = { offset -> },
                                onDragEnd = { },
                                onHorizontalDrag = { _, dragAmount -> left += dragAmount })
                        }
                        .background(Color.Transparent)
//                        .align(Alignment.TopStart)
                    ){

                    }

                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .width(60.dp)
                        .drawBehind {
                            drawLine(
                                Color.White,
                                Offset(right, 0f),
                                Offset(right, size.height),
                                strokeWidth = 4f
                            )

                        }
                        .offset(right.toDp() - 20.dp, 0.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .pointerInput(key1 = null,) {
                            detectHorizontalDragGestures(onDragStart = { offset -> },
                                onDragEnd = { },
                                onHorizontalDrag = { _, dragAmount -> right += dragAmount })
                        }
                        .background(Color.Transparent)
//                        .align(Alignment.TopEnd)
                    ){

                    }

//                graphTool(left = left, right = right,onChangeLeft={left=it}, onChangeRight={right= it})

            }
        }
        listGraph.forEachIndexed { i, item ->
            indicatorFactor(ticket = item, model = model ,color[i],range,scale,offset ,left,right,analysisMode, onListChange = {listPercent[i] = it}, onDateLeft = {dateleft = it},onDateRight = {dateright = it})
        }

        AnimatedVisibility(visible = !analysisMode) {
            Box(modifier = Modifier.fillMaxSize()){
                LazyColumn(modifier = Modifier.align(Alignment.CenterEnd)){
                    items(listRange){item ->
                        Spacer(modifier = Modifier.height(20.dp))
                        Box() {
                            var color:Color = if (range.equals(item)) colorLight.tertiaryLight else colorLight.secondaryLight
                            Text(text = item,color = Color.White, fontSize = 20.sp, modifier = Modifier
                                .clickable { range = item }
                                .background(color))
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                LazyRow(modifier=Modifier.align(Alignment.BottomStart)){
                    items(listGraph){item-> graphSlot(item = item, onchanged = {if(listGraph.size>1){listGraph-=it}},color[listGraph.indexOf(item)] ,onListChange ={listPercent.removeLast()})
                        Spacer(modifier = Modifier.size(2.dp))
                    }
                    item {

                        // Add panel

                        AnimatedContent(targetState = expanded, label = "") {target ->
                            if (target){
                                Column(verticalArrangement = Arrangement.Center,modifier = Modifier.align(
                                    Alignment.BottomEnd
                                )) {
                                    TextField(value = keyword.uppercase(), onValueChange = { keyword = it })
                                    Button(onClick = { expanded = !expanded;listGraph+=keyword.uppercase();listPercent.add(listGraph.indexOf(keyword.uppercase()),0.0);keyword=""}) {
                                        Text(text = "Submit")
                                    }
                                }
                            }else{
                                IconButton(onClick = { expanded = !expanded}) {
                                    Icon(Icons.Default.Add, contentDescription = "")
                                }
                            }
                        }
                    }
                }
            }
        }


//        Text(text = "${offset.x}", modifier = Modifier.align(Alignment.TopCenter))
//        Text(text = "${scale}", modifier = Modifier.align(Alignment.TopEnd))

    }

    }




@Composable
fun graphSlot(item: String, onchanged: (String) -> Unit, color: Color, onListChange: () -> Unit) {
    Box(modifier= Modifier
        .wrapContentHeight()
        .clip(RoundedCornerShape(30.dp))
        .background(color)){
        Row(verticalAlignment = Alignment.CenterVertically,modifier= Modifier
            .padding(5.dp)
            .wrapContentHeight()
            .align(Alignment.Center)) {
            Text(text = item)
            IconButton(onClick = { onchanged(item);
                onListChange()
            }) {
                Icon(Icons.Default.Close , contentDescription = "")
            }
//            Button(onClick = {onchanged(item)}, modifier = Modifier.wrapContentSize()) {
//                Text(text = "X", modifier = Modifier.wrapContentSize())
//            }
        }
    }

}


