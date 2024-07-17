package com.example.stockanalyst.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class box(
    @SerialName("chart")
    var chart: Chart
)

@Serializable
data class Chart(
    @SerialName("result")
    var result:List<Result>
)

@Serializable
data class Result(
    @SerialName("timestamp")
    var timestamp:List<Int>,

    @SerialName("indicators")
    var indicators: Indicators

)

@Serializable
data class Indicators(
    var quote:List<Quote>,
//    var adjclose:List<Adjclose>

)

@Serializable
data class Quote(
    var low:List<Double>,
    var close:List<Double>,
    var high:List<Double>,
    var open:List<Double>,
    var volume:List<Int>

)

//@Serializable
//data class Adjclose(
//    var adjclose:List<Double>
//)


data class stock_info(
    var ticker:String,
    var name:String,
    var exchange:String
)