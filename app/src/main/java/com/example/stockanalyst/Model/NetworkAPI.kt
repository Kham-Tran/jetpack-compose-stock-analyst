package com.example.stockanalyst.Model

import com.example.stockanalyst.Database.Ticket
import com.example.stockanalyst.Database.TicketDao
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Locale

//"validRanges": [
//"1d",
//"5d",
//"1mo",
//"3mo",
//"6mo",
//"1y",
//"2y",
//"5y",
//"10y",
//"ytd",
//"max"
//]

// Valid intervals: [1m, 2m, 5m, 15m, 30m, 60m, 90m, 1h, 1d, 5d, 1wk, 1mo, 3mo]

class NetworkAPI(TicketDAO:TicketDao) {
    var DAOclient:TicketDao = TicketDAO
    private var generalList:List<String> = emptyList()
    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
   private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(Json{
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
//                explicitNulls = true
            })
        }
    }

    //        System.currentTimeMillis()  -> current timestamp

    fun timeStampToDate(timestamp:Int):String{
        return simpleDateFormat.format(timestamp*1000L)
    }

   private suspend fun fetch(name:String,interval:String = "1d", range:String = "1d"):HttpResponse {
       return  client.get("https://query1.finance.yahoo.com/v8/finance/chart/${name}?metrics=high?&interval=${interval}&range=${range}")
    }

    suspend fun getTicketsPrice(name: String):Ticket{
        var NetData: box = fetch(name).body<box>()
        var TimeStamp = NetData.chart.result[0].timestamp
        var indicators = NetData.chart.result[0].indicators.quote[0]
        var tickets = Ticket(name.uppercase(),indicators.open[0],indicators.close[0],indicators.high[0],indicators.low[0],indicators.volume[0],TimeStamp[0])
        var listTicket = listOf(tickets)
        if (DAOclient.isExists(tickets.name.uppercase())){
            DAOclient.updateTicket(listTicket)
        }
        else { DAOclient.insertAll(listTicket)}
        return tickets
    }

    suspend fun getDatainRangeMini(name: String,range:String, interval:String): box {
        var data:box = fetch(name,interval,range).body<box>()
        return data
    }


}

