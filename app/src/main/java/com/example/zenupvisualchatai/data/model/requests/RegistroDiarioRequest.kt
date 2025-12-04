// RegistroDiarioRequest.kt
package com.example.zenupvisualchatai.data.model.requests

data class RegistroDiarioRequest(
    val idUsuario: Long,
    val humor: Int,
    val energia: Int,
    val estresse: Int
)
