package com.example.stockanalyst

import android.content.Context
import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockanalyst.Database.TicketDao
import com.example.stockanalyst.HomePage.HomePage
import com.example.stockanalyst.Model.ProductModel
import com.example.stockanalyst.TicketScreen.TicketPage

@Composable
fun Navigator(Nav:NavController, size:Size, dao :TicketDao, context: Context) {
    var selectedTicket by rememberSaveable {
        mutableStateOf("")
    }
    var model = ProductModel(dao,context)

    NavHost(navController = Nav as NavHostController, startDestination = Home.route){
        composable(Home.route){
            HomePage(Nav, size, dao, onchangeTicket = {selectedTicket = it.toString()},model,selectedTicket)
        }
        composable(TicketPage.route){
            TicketPage(Nav,dao,selectedTicket,model)
        }
    }
}