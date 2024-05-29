package com.example.stockanalyst.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TicketDao {
@Query("SELECT * FROM Ticket")
fun getallTickets(): List<Ticket>

@Query("SELECT * FROM Ticket WHERE name = :ticket")
fun getTicket(ticket:String): List<Ticket>

@Query("SELECT name FROM Ticket")
fun getTickerList():List<String>

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertAll(tickets:List<Ticket>)

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertOne(tickets:Ticket)

@Update
fun updateTicket(tickets: List<Ticket>)

@Delete
fun deleteAll(tickets: List<Ticket>)

@Query("SELECT EXISTS(SELECT * FROM Ticket WHERE name = :ticket)")
fun isExists(ticket:String): Boolean
}



@Dao
interface GraphDao{
    @Query("SELECT * FROM GraphData")
    fun getallGraphData(): List<GraphData>

    @Query("SELECT * FROM GraphData WHERE name =:name AND interval= :interval")
    fun getGraphData(name:String, interval:String): List<GraphData>

    @Insert
    fun insertAll(graph:List<GraphData>)

    @Delete
    fun deleteAll(graph:List<GraphData>)

    @Update
    fun updateAll(graph:List<GraphData>)
}
