package com.example.stockanalyst.Model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockanalyst.Database.Ticket
import com.example.stockanalyst.Database.TicketDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductModel(dao : TicketDao, context: Context):ViewModel() {
    private lateinit var Dao:TicketDao
    private lateinit var client: NetworkAPI
    var _TicketStateFlow = MutableStateFlow<List<com.example.stockanalyst.Database.Ticket>>(emptyList())
    var TicketStateFlow:StateFlow<List<Ticket>> = _TicketStateFlow
    var stockInfo: List<stock_info> = context.assets.open("data/stock_info.csv").bufferedReader().use {
        it.readLines().map { item ->
            var data = item.split(",")

            stock_info(data[0],data[1],data[2]) }
    }




    init {
        Dao = dao
        client = NetworkAPI(Dao)
        getPrices()
    }

    fun addItem(ticker:String){

        CoroutineScope(Dispatchers.Default).launch {
            var templateList = Dao.getTickerList()
            if(!templateList.contains(ticker)){
            var list: List<Ticket> = listOf( client.getTicketsPrice(ticker))
            _TicketStateFlow.value+=list
        }

       }}
    fun getPrices(){
        CoroutineScope(Dispatchers.Default).launch {
            var list: List<Ticket> = emptyList()
            var templateList = Dao.getTickerList()
            templateList.forEach{
                item ->
                list+=client.getTicketsPrice(item)

        }
            _TicketStateFlow.emit(list)  }


    }

    suspend fun getGraphData(name:String, range:String, interval:String): box {
        return client.getDatainRangeMini(name,range,interval)
    }
}