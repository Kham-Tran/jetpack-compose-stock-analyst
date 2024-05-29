package com.example.stockanalyst

interface Destination {
   val route:String
}

object Home:Destination{
    override val route = "Home"
}

object Profile:Destination{
    override val route = "Profile"
}

object Graph:Destination{
    override val route = "Graph"
}

object TicketPage:Destination{
    override val route = "TicketPage"
}

object Setting:Destination{
    override val route = "Setting"
}