package com.ucne.parcial2.ui.navegation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucne.parcial2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()

    ) {

        TopAppBar(
            title = { Text("Te Presto") },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.Logout , contentDescription = "Localized description")
                }

            },
            navigationIcon = {
                IconButton(onClick = {expanded = true  }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false })
                {
                    DropdownMenuItem(
                        text = { Text("Registro Tickets") },

                        onClick = {
                            navController.navigate(route = Rutas.TicketS.ruta ){
                                popUpTo("rutaHome")
                            }
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Add,
                                contentDescription = "Registro de tickets"
                            )
                        })
                    DropdownMenuItem(
                        text = { Text("Consulta") },
                        onClick = {
                            navController.navigate(route = Rutas.TicketC.ruta ){
                                popUpTo("rutaHome")

                            }
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.People,
                                contentDescription ="Consulta"
                            )
                        }
                    )
                }

            }

        )
        Image(painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Logo Inicio",
            modifier = Modifier
                .wrapContentSize(Alignment.CenterStart)
                .size(600.dp)

        )

    }
}

@Composable
fun NavigationGraph() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Rutas.Home.ruta )
    {
        composable(route = Rutas.Home.ruta){
            HomeScreen(navController)
        }
        composable(route = Rutas.TicketS.ruta){

            OcupacionScreen()
        }

        composable(route = Rutas.TicketC.ruta){
            PersonaScreen()
        }

        composable(route = Rutas.OcupacionC.ruta){
            ConsultaOcupacionScreen(onNew = {navController.navigate(route= Rutas.OcupacionR.ruta)})
        }
    }
}