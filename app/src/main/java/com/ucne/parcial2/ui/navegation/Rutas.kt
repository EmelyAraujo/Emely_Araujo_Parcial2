package com.ucne.parcial2.ui.navegation

sealed class Rutas(var ruta: String){
    object Home: Rutas( "ui")
    object TicketS: Rutas("tickets")
    object  TicketC: Rutas("tickets_list")
}