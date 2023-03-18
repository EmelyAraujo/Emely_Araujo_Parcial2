package com.ucne.parcial2.ui.navegation

sealed class Rutas(var ruta: String){
    object Home: Rutas( "rutaHome")
    object TicketS: Rutas("RutaTicket"){
    }

    object  TicketC: Rutas("RutaConsulta")

}