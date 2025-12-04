// LoginViewModel.kt
package com.example.zenupvisualchatai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenupvisualchatai.data.repository.AuthRepository
import com.example.zenupvisualchatai.data.model.requests.LoginRequest
import com.example.zenupvisualchatai.data.model.responses.LoginResponse
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

    fun fazerLogin(chave: String) {
        // Validação local
        if (chave.isBlank()) {
            _uiState.value = LoginUiState.Error("A chave é obrigatória.")
            return
        }

        val request = LoginRequest(chave = chave)

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val response = repository.login(request)
                _uiState.value = LoginUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Erro desconhecido ao fazer login"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}