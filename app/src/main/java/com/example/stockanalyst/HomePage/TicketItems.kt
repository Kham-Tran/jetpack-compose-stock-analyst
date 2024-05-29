package com.example.stockanalyst.HomePage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockanalyst.Database.Ticket
import com.example.stockanalyst.TicketPage
import com.example.stockanalyst.TicketScreen.TicketPage
import kotlinx.coroutines.channels.TickerMode
import java.math.RoundingMode
import java.security.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.InstantSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ticketItem(
    nav: NavController,
    ticket: Ticket,
    selectedTicket: String,
    onchangeTicket: (String) -> Unit
) {
    var color:Color = if(ticket.open > ticket.close) Color.Red else Color.Green
    Card(onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(color),
        modifier = Modifier.padding(5.dp).fillMaxWidth().height(50.dp)) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(5.dp).fillMaxWidth()
            .clickable {
                onchangeTicket(ticket.name)
                nav.navigate(TicketPage.route)
            })
    {
        Text(text = ticket.name,
            color = Color.White,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 30.sp
            ))

        Text(text = "${ticket.date}",
            color = Color.White,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 5.sp
            ))

        Text(text = "$ ${roundOffDecimal(ticket.close)}",
            color = Color.White,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 30.sp
            ))
    }
    }
}

fun roundOffDecimal(number: Double): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}