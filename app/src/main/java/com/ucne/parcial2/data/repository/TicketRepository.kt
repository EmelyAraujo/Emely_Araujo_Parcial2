package com.ucne.parcial2.data.repository

import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun getTicket(): Flow<Resource<List<TicketDto>>>

    suspend fun putTicket(id: Int, ticketDto: TicketDto)

    suspend fun postTickets(ticketDto: TicketDto)

    suspend fun deleteTicket(id: Int)



}