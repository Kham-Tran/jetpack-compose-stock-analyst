package com.example.stockanalyst.HomePage

import android.util.Size
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stockanalyst.Model.ProductModel
import com.example.stockanalyst.Model.stock_info

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun searchFloatingButton(
    size: Size,
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    nav: NavController,
    model: ProductModel
){
    FloatingActionButton(onClick = { /*TODO*/ }, containerColor = MaterialTheme.colorScheme.secondaryContainer, elevation = FloatingActionButtonDefaults.elevation(0.dp) , modifier = Modifier) {
        AnimatedContent(targetState = expanded, label = "") { targetState ->
            if(targetState){
                searchBox(nav = nav, size = size, expanded = expanded, onExpanded = onExpanded, model)

            }
            else{
                IconButton(onClick = { onExpanded(!expanded) }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search button")
                }

            }
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBox(
    nav: NavController,
    size: Size,
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    model: ProductModel
) {
    var keyword by remember {
        mutableStateOf("")
    }

    var filList = mutableListOf<stock_info>()
    if (keyword.isNotEmpty()) {
        filList =
            model.stockInfo.filter { it.ticker.startsWith(keyword) || it.name.uppercase().contains(keyword) }
                .toMutableList()
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth(.95f)
        .wrapContentHeight()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .heightIn(50.dp, 300.dp)
                .background(Color.Transparent)
        ) {
            items(items = filList, itemContent = { item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(30.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = item.ticker, modifier = Modifier.weight(.1f))
                    Text(
                        text = item.name,
                        maxLines = 1,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                    )
                    IconButton(onClick = {
                        model.addItem(item.ticker)
                        onExpanded(!expanded)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
            })
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = { onExpanded(!expanded) }))
        Row(){
            TextField(
                value = keyword,
                onValueChange = {
                    keyword = it.uppercase()
                },
                placeholder = { Text(text = "Enter Symbol") },
                modifier = Modifier
            )
            Button(onClick = { model.addItem(keyword)
                onExpanded(!expanded)}) {
                Text(text = "Submit")
            }
        }
    }

    }

