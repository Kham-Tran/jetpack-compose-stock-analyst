package com.example.stockanalyst.Database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ticket")
class Ticket {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id:Int = 0

    @ColumnInfo(name = "name")
    var name:String = ""

    @ColumnInfo(name = "open")
    var open:Double = 0.0

    @ColumnInfo(name = "close")
    var close:Double = 0.0

    @ColumnInfo(name = "high")
    var high:Double = 0.0

    @ColumnInfo(name = "low")
    var low:Double = 0.0

    @ColumnInfo(name = "volume")
    var volume:Int = 0

    @ColumnInfo(name = "date")
    var date: Int = 0

    constructor(
        name:String,
        open:Double = -1.0,
        close:Double = -1.0,
        high:Double = -1.0,
        low:Double = -1.0,
        volume: Int,
        date: Int
    ){
        this.name = name
        this.open = open
        this.close = close
        this.high = high
        this.low = low
        this.volume = volume
        this.date = date
    }
}

@Entity(tableName = "GraphData")
class GraphData{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id:Int = 0

    @ColumnInfo(name = "name")
    var name:String = ""

    @ColumnInfo(name="range")
    var range:String = ""

    @ColumnInfo(name = "interval")
    var interval:String=""

    @ColumnInfo(name = "date")
    var date: Long = 0

    @ColumnInfo(name = "price")
    var price:Double = 0.0

    constructor(name: String,range:String,interval:String,date: Long,price: Double)
    {
        this.name = name
        this.price = price
        this.date = date
        this.interval=interval
        this.range = range
    }

}



@Entity(tableName = "lastDay")
class LastDate{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    var name:String = ""

    @ColumnInfo(name = "LastDayUpdate")
    var date:Long= 0

    @ColumnInfo(name="range")
    var range:String = ""

    @ColumnInfo(name = "interval")
    var interval:String=""

    constructor(name: String,date: Long, range:String, interval:String)
    {
        this.name = name
        this.date = date
        this.range = range
        this.interval = interval
    }
}