package com.example.ecoreport.ui_comp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.ReporteEntity
import com.example.ecoreport.model.EstadoReporte
import com.example.ecoreport.model.FiltrosReporte

@Composable
fun PantallaReportes(
    reportes: List<ReporteEntity>,
    filtros: FiltrosReporte,
    aplicarFiltros: (String?, String?) -> Unit,
    seleccionarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {
    var estadoSeleccionado by remember(filtros.estado) {
        mutableStateOf(filtros.estado)
    }
    var categoriaSeleccionada by remember(filtros.categoria) {
        mutableStateOf(filtros.categoria.orEmpty())
    }
    var menuEstadoExpandido by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Reportes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.weight(1.2f)
            ) {
                OutlinedButton(
                    onClick = {
                        menuEstadoExpandido = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = estadoSeleccionado
                            ?.replace("_", " ")
                            ?: "Todos",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DropdownMenu(
                    expanded = menuEstadoExpandido,
                    onDismissRequest = {
                        menuEstadoExpandido = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Todos los estados")
                        },
                        onClick = {
                            estadoSeleccionado = null
                            menuEstadoExpandido = false
                        }
                    )

                    EstadoReporte.values().forEach { estado ->
                        DropdownMenuItem(
                            text = {
                                Text(estado.name.replace("_", " "))
                            },
                            onClick = {
                                estadoSeleccionado = estado.name
                                menuEstadoExpandido = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = categoriaSeleccionada,
                onValueChange = {
                    categoriaSeleccionada = it
                },
                label = {
                    Text("Categoría")
                },
                modifier = Modifier.weight(1.5f),
                singleLine = true
            )

            Button(
                onClick = {
                    aplicarFiltros(
                        estadoSeleccionado,
                        categoriaSeleccionada
                    )
                },
                modifier = Modifier.weight(0.9f)
            ) {
                Text(
                    text = "Buscar",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (reportes.isEmpty()) {

            Text(
                text = "No se encontraron reportes con los filtros seleccionados."
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = reportes,
                    key = { reporte ->
                        reporte.id
                    }
                ) { reporte ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                seleccionarReporte(reporte)
                            },

                        shape = RoundedCornerShape(16.dp),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {

                            Text(
                                text = reporte.titulo,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Categoría: ${reporte.categoria}"
                            )

                            Text(
                                text = "Ubicación: ${reporte.ubicacion}"
                            )

                            Text(
                                text = "Estado: ${reporte.estado}"
                            )

                            Text(
                                text = "Fecha: ${reporte.fecha}"
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Presiona para ver el detalle",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "VOLVER"
            )
        }
    }
}