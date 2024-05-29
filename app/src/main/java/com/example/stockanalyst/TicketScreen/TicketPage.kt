package com.example.stockanalyst.TicketScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.stockanalyst.Database.TicketDao
import com.example.stockanalyst.Home
import com.example.stockanalyst.Model.ProductModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketPage(nav: NavController, dao: TicketDao, selectedTicket: String, model: ProductModel) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = selectedTicket)},
            navigationIcon = {
                IconButton(onClick = { nav.navigate(Home.route) }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "profile")
                }
            }
            ) },
    ) {
            innerpadding ->
        LazyColumn( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(innerpadding)){
            item {
                miniGraph(model,nav)
            }
            item { 
                Text(text = "Ticker Description")
            }
        }
    }
}