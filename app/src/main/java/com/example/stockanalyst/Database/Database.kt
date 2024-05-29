package com.example.stockanalyst.Database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Ticket::class)], version = 1, exportSchema = true)
abstract class TicketDatabase:RoomDatabase() {
    abstract fun ticketDAO(): TicketDao
    companion object{
        private var INSTANCE:TicketDatabase? = null
        fun getInstance(context: Context):TicketDatabase{
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TicketDatabase::class.java,"StockAnalysisDB"
                ).build()
                INSTANCE = instance
            }
            return instance
//            autoMigrations = [AutoMigration(from = 1, to = 2)]
        }
    }
}