package com.example.ecoreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoreport.data.ReporteEntity
import com.example.ecoreport.repository.ReporteRepository
import kotlinx.coroutines.launch

class ReporteViewModel (
    private val repository: ReporteRepository
): ViewModel() {
    val reportes = mutableListOf<ReporteEntity>()

    init {
        cargarReportes()
    }

    fun cargarReportes() {
        viewModelScope.launch {
            val datos = repository.obtenerTodos()

            reportes.clear()

            reportes.addAll(datos)
        }
    }

    fun insertar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.insertar(reporte)
            cargarReportes()
        }
    }

    fun eliminar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.eliminar(reporte)

            cargarReportes()
        }
    }

    fun actualizar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.actualizar(reporte)

            cargarReportes()
        }
    }

    fun filtrarPorEstado(
        estado: String?
    ) {
        viewModelScope.launch {

            val datos = if (estado == null) {
                repository.obtenerTodos()
            } else {
                repository.obtenerPorEstado(estado)
            }

            reportes.clear()
            reportes.addAll(datos)
        }
    }
}