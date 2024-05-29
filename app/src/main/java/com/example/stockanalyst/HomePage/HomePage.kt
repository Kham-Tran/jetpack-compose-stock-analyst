package com.example.stockanalyst.HomePage

import android.util.Size
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.stockanalyst.Database.TicketDao
import com.example.stockanalyst.Model.ProductModel
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    nav: NavController, size: Size, dao: TicketDao,
    onchangeTicket: (String) -> Unit, model: ProductModel, selectedTicket: String
){

    val listTicket by model.TicketStateFlow.collectAsState()
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = { topBar(nav = nav)},
        floatingActionButton = { searchFloatingButton(size,expanded, onExpanded={expanded = it},nav, model) },
        modifier = Modifier.clickable { if(expanded){expanded = false} }
    ) {
        innerpadding ->
        Box(modifier = Modifier.padding(innerpadding)){
            LazyColumn(){
                items(listTicket){item ->  ticketItem(nav = nav, ticket = item,selectedTicket,onchangeTicket)}
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(nav: NavController){
    TopAppBar(
        title = { Text(text = "HomePage") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "profile")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Setting")
            }
        })
}
