package com.ucne.parcial2.ui.tickets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucne.parcial2.data.remote.dto.TicketDto
import com.ucne.parcial2.ui.navegation.Rutas
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    ticketId: Int,
    viewModel: TicketViewModelApi = hiltViewModel(),
    sendData: () -> Unit
) {
    remember {
        viewModel.setTicket(ticketId)
        0
    }
    Column(Modifier.fillMaxWidth()) {

        TicketBody(viewModel = viewModel) {
            sendData()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBody(
    viewModel: TicketViewModelApi,
    sendData: () -> Unit,
) {
    val navController: NavHostController = rememberNavController()
    var expanded by remember { mutableStateOf(false) }
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
                        navController.navigate(Rutas.TicketC.ruta)
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

        OutlinedTextField(

            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp),
            value = viewModel.estatus,
            enabled = false, readOnly = true,
            label = { Text(text = "Estatus") },
            onValueChange = { viewModel.estatus = it })

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)

        ) {
            viewModel.opcionesEstatus.forEach { opcion ->
                DropdownMenuItem(
                    text = {
                        Text(text = opcion, textAlign = TextAlign.Center)
                    },
                    onClick = {
                        expanded = false
                        viewModel.estatus = opcion
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }

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
                    .padding(8.dp),

                content = { Icon(imageVector = Icons.Filled.Save, contentDescription = "Save") },
                onClick = {
                    viewModel.putTicket()
                    sendData()
                }
            )
        }
    }
}

