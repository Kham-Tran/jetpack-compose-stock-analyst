package com.example.stockanalyst

import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.stockanalyst.Database.Ticket
import com.example.stockanalyst.Database.TicketDao
import com.example.stockanalyst.Database.TicketDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         var TicketDb: TicketDatabase = TicketDatabase.getInstance(this)
         var TicketDAO: TicketDao = TicketDb.ticketDAO()
        setContent {
            AppTheme{
                // A surface container using the 'background' color from the theme
                var context = LocalContext.current
                var width = context.resources.displayMetrics.widthPixels
                var height = context.resources.displayMetrics.heightPixels
                var size = Size(width,height)
                var db = TicketDatabase.getInstance(context)
                var dao = db.ticketDAO()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation(size, dao)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

@Composable
fun MyNavigation(size:Size, dao:TicketDao){
    var NavController = rememberNavController()
    Navigator(NavController, size, dao, LocalContext.current)
}