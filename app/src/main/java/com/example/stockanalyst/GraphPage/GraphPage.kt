package com.example.stockanalyst.GraphPage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose.colorLight
import com.example.stockanalyst.Model.ProductModel
import com.example.stockanalyst.TicketPage

@Composable
fun GraphPage(Nav: NavHostController, model: ProductModel, selectedTicket: String) {
    var listRange = listOf<String>("1W","3MO","3Y")
    var color = listOf<Color>(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta)
    var listGraph by rememberSaveable {
        mutableStateOf(listOf(selectedTicket))
    }
    var scaleNum by rememberSaveable {
        mutableStateOf(0f)
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
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }
    var state = rememberTransformableState(onTransformation = {zoomChange, panChange, rotationChange ->
        scale*=zoomChange
        rotation += rotationChange
        offset += panChange})

    var configuration = LocalConfiguration.current

    Box(modifier = Modifier
        .fillMaxSize()
        .transformable(state = state)
        .background(color = colorLight.outlineLight)) {
        IconButton(onClick = { Nav.navigate(TicketPage.route) }) {
            Text(text = "Back")
        }


        listGraph.forEachIndexed { index, item ->
            indicatorFactor(ticket = item, model = model ,color[index],range,scale,offset)
        }


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
            items(listGraph){item-> graphSlot(item = item, onchanged = {if(listGraph.size>1){listGraph-=it}},color[listGraph.indexOf(item)])
                Spacer(modifier = Modifier.size(2.dp))
            }
            item {
                AnimatedContent(targetState = expanded, label = "") {target ->
                    if (target){
                        Column(verticalArrangement = Arrangement.Center,modifier = Modifier.align(
                            Alignment.BottomEnd
                        )) {
                            TextField(value = keyword.uppercase(), onValueChange = { keyword = it })
                            Button(onClick = { expanded = !expanded;listGraph+=keyword.uppercase();keyword=""}) {
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


        Text(text = "${offset.x}", modifier = Modifier.align(Alignment.TopCenter))
        Text(text = "${scale}", modifier = Modifier.align(Alignment.TopEnd))

    }

    }




@Composable
fun graphSlot(item: String, onchanged: (String) -> Unit, color: Color) {
    Box(modifier= Modifier
        .wrapContentHeight()
        .clip(RoundedCornerShape(30.dp))
        .background(color)){
        Row(verticalAlignment = Alignment.CenterVertically,modifier= Modifier
            .padding(5.dp)
            .wrapContentHeight()
            .align(Alignment.Center)) {
            Text(text = item)
            IconButton(onClick = { onchanged(item) }) {
                Icon(Icons.Default.Close , contentDescription = "")
            }
//            Button(onClick = {onchanged(item)}, modifier = Modifier.wrapContentSize()) {
//                Text(text = "X", modifier = Modifier.wrapContentSize())
//            }
        }
    }

}


