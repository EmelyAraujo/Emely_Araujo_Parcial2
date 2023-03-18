package com.ucne.parcial2.data.repository

import com.ucne.parcial2.data.local.entity.toTicketDto
import com.ucne.parcial2.data.remote.TePrestoApi
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


import javax.inject.Inject

class TicketRepositoryImp @Inject constructor(
    private val api: TePrestoApi
): TicketRepository {

    override  fun getTickets(): Flow<Resource<List<TicketDto>>> = flow {
        try {
            emit(Resource.Loading())

            val tickets =
                api.getTickets()

            emit(Resource.Success(tickets))
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: java.io.IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override suspend fun putTickets(id: Int, ticketDto: TicketDto) {
        api.putTickets(id,ticketDto)
    }

    override suspend fun postTickets(ticketDto: TicketDto) {
        api.postTickets(ticketDto)
    }

    override suspend fun deleteTicket(id: Int) {
        api.deleteTickets(id)
    }

    override  fun getTicketsbyId(id: Int): Flow<Resource<TicketDto>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val tickets =
                api.getTicketsbyId(id) //descarga las ocupaciones de internet, se supone quedemora algo

            emit(Resource.Success(tickets)) //indicar que se cargo correctamente y pasarle las monedas
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: java.io.IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }
}