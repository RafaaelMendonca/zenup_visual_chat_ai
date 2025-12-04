// LoginViewModel.kt
package com.example.zenup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenup.data.repository.AuthRepository
import com.example.zenup.data.model.LoginRequest
import com.example.zenup.data.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Define os estados da UI para Login
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val response: LoginResponse) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun fazerLogin(email: String, senha: String) {

        // Validação local
        if (email.isBlank() || senha.isBlank()) {
            _uiState.value = LoginUiState.Error("Email e senha são obrigatórios.")
            return
        }

        val request = LoginRequest(email = email, senha = senha)

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val response = repository.login(request)
                // O sucesso contém o token e o ID do usuário, que devem ser salvos.
                _uiState.value = LoginUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Falha no login: ${e.message ?: "Erro desconhecido"}")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}