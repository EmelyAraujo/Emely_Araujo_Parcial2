package com.ucne.parcial2.data.repository

import com.ucne.parcial2.data.local.entity.TicketEntity
import com.ucne.parcial2.data.local.entity.toTicketDto
import com.ucne.parcial2.data.remote.TePrestoApi
import com.ucne.parcial2.data.remote.dto.TicketDto
import javax.inject.Inject

class Ticket2Repository @Inject constructor(
    private  val ticketsDto: TicketDto,
    private  val ticketsApi: TePrestoApi
) {
    suspend fun insert(ticket: TicketEntity) {


        ticketsApi.putTickets(ticket.ticketId!!, ticket.toTicketDto())


        suspend fun putTicket(id: Int, ticketsDto: TicketDto) =
            ticketsApi.putTickets(id, ticketsDto)

    }
}

