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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

data class TicketListState(
    val isLoading: Boolean = false,
    val tickets: List<TicketDto> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class TicketViewModelApi @Inject constructor(
    private val ticketRepositoryImp: TicketRepositoryImp,
    data: TicketDto?
) : ViewModel() {
    var asunto by   mutableStateOf(data?.asunto ?:"")
    var empresa  by   mutableStateOf(data?.empresa ?:"")
    var encargadoId by  mutableStateOf(data?.encargadoId.toString()?:"")
    var especificaciones by mutableStateOf(data?.especificaciones?:"")
    var estatus by   mutableStateOf(data?.estatus?: "")
    var fecha by   mutableStateOf(data?.fecha?:"")
    var orden by   mutableStateOf("")
    var ticketId by mutableStateOf("")


    var uiState = MutableStateFlow(TicketListState())
        private set

    init {

        viewModelScope.launch {
            try {
                ticketRepositoryImp.getTicket().onEach { result ->
                    when(result){
                        is Resource.Loading ->{
                            uiState.update {
                                it.copy(isLoading = true)
                            }
                        }
                        is Resource.Success -> {
                            uiState.update {
                                it.copy(tickets = result.data ?: emptyList())
                            }
                        }

                        is Resource.Error -> {
                            uiState.update {
                                it.copy(error = result.message ?: "Error desconocido")
                            }
                        }

                    }
                }
            }catch (e: Exception) {
                println("Esto no sirve jjj, arreglalo")
            }
        }

    }

    fun putData(id: Int,ticketDto: TicketDto){
        viewModelScope.launch {
            try{
                ticketRepositoryImp.putTicket(id, ticketDto)
            } catch (e: Exception){
                println("Se rompio esta vaina, arreglalo")
            }
        }
    }

    fun deleteData(id: Int){
        viewModelScope.launch {
            try{
                ticketRepositoryImp.deleteTicket(id)
            }catch(e: Exception){}
            println("Arreglalo se daño")
        }
    }

    fun postData(ticketDto: TicketDto){
        viewModelScope.launch {
            try {
                ticketRepositoryImp.postTickets(ticketDto)
            }catch (e: Exception){
                println("Arreglalo ta dañao")
            }
        }
    }


}