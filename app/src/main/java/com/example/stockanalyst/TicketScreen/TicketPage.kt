package com.example.stockanalyst.TicketScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            ) },
    ) { innerpadding ->
        Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.primary)){
            Column( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(innerpadding)){
                LaunchedEffect(key1 = null){

                }
                miniGraph(model,nav,selectedTicket)
                Text(text = "Ticker Description")

            }
        }

    }
}