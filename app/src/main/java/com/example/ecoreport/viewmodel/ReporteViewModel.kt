package com.example.ecoreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoreport.data.ReporteEntity
import com.example.ecoreport.data.remote.ReporteDto
import com.example.ecoreport.repository.ReporteRepository
import com.google.gson.JsonParseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ReporteViewModel (
    private val repository: ReporteRepository
): ViewModel() {

    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)

    private val _reportesRemotos = MutableStateFlow<List<ReporteDto>>(emptyList())
    val reportesRemotos: StateFlow<List<ReporteDto>> = _reportesRemotos

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada
    val reportes:
    StateFlow<List<ReporteEntity>> =
        _categoriaSeleccionada
            .flatMapLatest {
                categoria ->
                repository.obtenerPorCategoria(categoria)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted
                    .WhileSubscribed(5000),
                initialValue = emptyList()
            )



    fun insertar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.insertar(reporte)
        }
    }

    fun eliminar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.eliminar(reporte)
        }
    }

    fun actualizar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.actualizar(reporte)
        }
    }

    fun seleccionarCategoria(categoria: String?) {
        _categoriaSeleccionada.value = categoria
    }

    fun cargarReportesRemotos() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                _reportesRemotos.value = repository.obtenerReportesRemotos()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                _error.value = "No se pudo conectar con el servidor. Verifica tu conexión e inténtalo de nuevo."
            } catch (exception: HttpException) {
                _error.value = "El servidor respondió con un error (${exception.code()}). Inténtalo de nuevo más tarde."
            } catch (exception: JsonParseException) {
                _error.value = "La respuesta del servidor no tiene el formato esperado."
            } finally {
                _cargando.value = false
            }
        }
    }
}