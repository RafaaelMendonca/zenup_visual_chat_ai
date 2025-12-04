package com.example.zenup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenup.data.repository.RegistroRepository
import org.example.model.RegistroDiarioRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 1. Estado para Gerenciar os Inputs do Usuário nas 3 Telas
data class RegistroDiarioState(
    val humor: Int? = null,        // Valores de 1-6
    val energia: Int? = null,      // Valores de 1-5
    val estresse: Int? = null,     // Valores de 1-5
    val idUsuario: Long? = null    // ID do usuário logado
)

// 2. Estado para Gerenciar a Comunicação Final com a API
sealed class RegistroApiState {
    object Idle : RegistroApiState()
    object Loading : RegistroApiState()
    data class Success(val message: String) : RegistroApiState()
    data class Error(val message: String) : RegistroApiState()
}

class RegistroDiarioViewModel(
    private val repository: RegistroRepository = RegistroRepository()
) : ViewModel() {

    // Mantém o estado dos inputs do usuário nas 3 telas
    private val _registroState = MutableStateFlow(RegistroDiarioState())
    val registroState: StateFlow<RegistroDiarioState> = _registroState

    // Mantém o estado da chamada final à API
    private val _apiState = MutableStateFlow<RegistroApiState>(RegistroApiState.Idle)
    val apiState: StateFlow<RegistroApiState> = _apiState

    // Funções para salvar o input de cada tela
    fun setIdUsuario(id: Long) {
        _registroState.value = _registroState.value.copy(idUsuario = id)
    }

    fun setHumor(humor: Int) {
        _registroState.value = _registroState.value.copy(humor = humor)
    }

    fun setEnergia(energia: Int) {
        _registroState.value = _registroState.value.copy(energia = energia)
    }

    fun setEstresse(estresse: Int) {
        _registroState.value = _registroState.value.copy(estresse = estresse)
    }

    // Função para enviar o registro completo ao final
    fun enviarRegistroDiario() {
        val state = _registroState.value

        // Validação
        if (state.idUsuario == null || state.humor == null || state.energia == null || state.estresse == null) {
            _apiState.value = RegistroApiState.Error("Dados incompletos para o registro.")
            return
        }

        val request = RegistroDiarioRequest(
            idUsuario = state.idUsuario,
            humor = state.humor,
            energia = state.energia,
            estresse = state.estresse
        )

        viewModelScope.launch {
            _apiState.value = RegistroApiState.Loading
            try {
                val response = repository.registrarCheckIn(request)
                _apiState.value = RegistroApiState.Success(response.mensagem)
                // Limpa os dados mas mantém o ID do usuário
                _registroState.value = RegistroDiarioState(idUsuario = state.idUsuario)
            } catch (e: Exception) {
                _apiState.value = RegistroApiState.Error("Falha ao registrar: ${e.message ?: "Erro desconhecido"}")
            }
        }
    }

    fun resetApiState() {
        _apiState.value = RegistroApiState.Idle
    }
}