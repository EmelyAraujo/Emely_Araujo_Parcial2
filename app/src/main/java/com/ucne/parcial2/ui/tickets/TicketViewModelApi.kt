package com.ucne.parcial2.ui.tickets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.data.repository.TicketRepositoryImp
import com.ucne.parcial2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

data class TicketListState(
    val isLoading: Boolean = false,
    val tickets: List<TicketDto> = emptyList(),
    val error: String = ""
)
data class TicketsState(
    val isLoading: Boolean = false,
    val ticket: TicketDto ? =  null,
    val error: String = ""
)

@HiltViewModel
class TicketViewModelApi @Inject constructor(
    private val ticketRepositoryImp: TicketRepositoryImp,
) : ViewModel() {
    var asunto by   mutableStateOf("")
    var empresa  by   mutableStateOf("")
    var encargadoId by  mutableStateOf("")
    var especificaciones by mutableStateOf("")
    var estatus by   mutableStateOf( "")
    var fecha by   mutableStateOf("")
    var orden by   mutableStateOf("")
    var ticketId by mutableStateOf(0)
    val opcionesEstatus = listOf("Solicitado", "En espera", "Finalizado")


    var uiState = MutableStateFlow(TicketListState())
        private set
    var uiStateTicket = MutableStateFlow(TicketsState())
        private set

    init {
        ticketRepositoryImp.getTickets().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    uiState.update {
                        it.copy(tickets = result.data ?: emptyList())
                    }
                }
                is Resource.Error -> {
                    uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)

    }
    fun setTicket(id: Int) {
        ticketId = id
        ticketRepositoryImp.getTicketsbyId(ticketId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiStateTicket.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    uiStateTicket.update {
                        it.copy(ticket = result.data)
                    }
                    empresa = uiStateTicket.value.ticket!!.empresa
                    asunto = uiStateTicket.value.ticket!!.asunto
                    estatus = uiStateTicket.value.ticket!!.estatus
                    especificaciones = uiStateTicket.value.ticket!!.especificaciones
                }
                is Resource.Error -> {
                    uiStateTicket.update {
                        it.copy(
                            error = result.message ?: "Error desconocido"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun putTicket() {
        viewModelScope.launch {
            ticketRepositoryImp.putTickets(
                ticketId, TicketDto(
                    asunto,
                    empresa,
                    uiStateTicket.value.ticket!!.encargadoId,
                    especificaciones,
                    estatus, uiStateTicket.value.ticket!!.fecha,
                    uiStateTicket.value.ticket!!.orden,
                    ticketId = ticketId
                )
            )
        }

    }

    fun deleteData(id: Int) {
        viewModelScope.launch {
            try {
                ticketRepositoryImp.deleteTicket(id)
            } catch (e: Exception) {
            }
            println("Arreglalo se daño")
        }
    }

    fun postData(ticketDto: TicketDto) {
        viewModelScope.launch {
            try {
                ticketRepositoryImp.postTickets(ticketDto)
            } catch (e: Exception) {
                println("Arreglalo ta dañao")
            }
        }
    }
}