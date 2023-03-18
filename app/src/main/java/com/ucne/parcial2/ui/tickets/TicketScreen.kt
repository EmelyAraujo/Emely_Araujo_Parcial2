package com.ucne.parcial2.ui.tickets

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.twotone.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ucne.parcial2.data.remote.dto.TicketDto
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBody(
    putData: (TicketDto)-> Unit,
    getData: (TicketDto)-> Unit,
    postData: (TicketDto)-> Unit,
    deleteData: (TicketDto) -> Unit,
    data: TicketDto?
) {
    val viewModel: TicketViewModelApi = viewModel()



    val scope = rememberCoroutineScope()
    val anio: Int
    val mes: Int
    val dia: Int


    /*
    val mCalendar = Calendar.getInstance()
    anio = mCalendar.get(Calendar.YEAR)
    mes = mCalendar.get(Calendar.MONTH)
    dia = mCalendar.get(Calendar.DAY_OF_MONTH)
    */

    /*val mDatePickerDialog = DatePickerDialog(
        LocalContext.current, { _: DatePicker, anio: Int, mes: Int, dia: Int ->
            fecha = "$dia/${mes + 1}/$anio"
        }, anio, mes, dia
    )*/
    /*----------------------------------------Code Start------------------------------------------------------*/
    Column(modifier = Modifier.fillMaxWidth())
    {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp, 30.dp)
                .padding(4.dp)
                .clickable {
                    scope.launch {
                        navController.navigate(HomeScreen.TicketsList.route)
                    }
                }
        )

        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Registro de Tickets", fontSize = 27.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.asunto,
            onValueChange = { viewModel.asunto = it },
            label = { Text("Asunto") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.empresa,
            onValueChange = { viewModel.empresa = it },
            label = { Text("Empresa") })

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.encargadoId,
            onValueChange = { viewModel.encargadoId = it },
            label = { Text("Encargado") })

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.especificaciones,
            onValueChange = { viewModel.especificaciones = it },
            label = { Text("Especificaciones") })

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.estatus,
            onValueChange = { viewModel.estatus = it },
            label = { Text("Estatus") })

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.fecha,
            onValueChange = { viewModel.fecha = it },
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .padding(4.dp)
                        .clickable {
                           // mDatePickerDialog.show()
                        })
            },
            label = { Text(text = "Fecha") }
        )

        OutlinedTextField(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = viewModel.orden,
            onValueChange = { viewModel.orden = it },
            label = { Text("Orden") }
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomCenter)
        ) {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .align(Alignment.CenterHorizontally)
                    .wrapContentSize(Alignment.Center),
                text = { Text("Guardar") },
                icon = { Icon(imageVector = Icons.Filled.Save, contentDescription = "Save") },
                onClick = {
                    val newData = TicketDto(viewModel.asunto, viewModel.empresa, viewModel.encargadoId.toInt(),viewModel.estatus, viewModel.fecha, viewModel.orden)
                    viewModel.putData(viewModel.ticketId.toInt(),newData)
                }
            )
        }
    }
}
}