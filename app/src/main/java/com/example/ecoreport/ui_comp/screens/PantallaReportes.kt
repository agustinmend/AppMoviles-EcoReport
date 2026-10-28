package com.example.ecoreport.ui_comp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.ReporteEntity

@Composable
fun PantallaReportes(
    reportes: List<ReporteEntity>,
    categoriaSeleccionada: String?,
    seleccionarCategoria: (String?) -> Unit,
    seleccionarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {
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

        Text(
            text = "Filtrar por categoría",
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row (
            horizontalArrangement =
                Arrangement.spacedBy(4.dp)
        ) {

            FilterChip(
                selected =
                    categoriaSeleccionada == null,

                onClick = {

                    seleccionarCategoria(
                        null
                    )
                },

                label = {
                    Text("TODOS")
                }
            )

            FilterChip(
                selected =
                    categoriaSeleccionada ==
                            "alumbrado",

                onClick = {

                    seleccionarCategoria(
                        "alumbrado"
                    )
                },

                label = {
                    Text("ALUMBRADO")
                }
            )

            FilterChip(
                selected =
                    categoriaSeleccionada ==
                            "AGUAS",

                onClick = {

                    seleccionarCategoria(
                        "AGUAS"
                    )
                },

                label = {
                    Text("AGUAS")
                }
            )

            FilterChip(
                selected =
                    categoriaSeleccionada ==
                            "SUELOS",

                onClick = {

                    seleccionarCategoria(
                        "SUELOS"
                    )
                },

                label = {
                    Text("SUELOS")
                }
            )
        }

        if (reportes.isEmpty()) {

            Text(
                text = "No existen reportes registrados."
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