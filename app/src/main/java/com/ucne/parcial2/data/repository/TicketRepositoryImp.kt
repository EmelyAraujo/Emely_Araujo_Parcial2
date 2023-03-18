package com.ucne.parcial2.data.repository

import com.ucne.parcial2.data.remote.TePrestoApi
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException


import javax.inject.Inject

class TicketRepositoryImp @Inject constructor(
    private val api: TePrestoApi
): TicketRepository {

    override suspend fun getTicket(): Flow<Resource<List<TicketDto>>> = flow {

        try{
            emit(Resource.Loading())
            val ticket = api.getTickets()

            emit(Resource.Success(ticket))
        }catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error de Http General"))
        }catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override suspend fun putTicket(id: Int, ticketDto: TicketDto) {
        api.putTickets(id,ticketDto)
    }

    override suspend fun postTickets(ticketDto: TicketDto) {
        api.postTickets(ticketDto)
    }

    override suspend fun deleteTicket(id: Int) {
        api.deleteTickets(id)
    }
}