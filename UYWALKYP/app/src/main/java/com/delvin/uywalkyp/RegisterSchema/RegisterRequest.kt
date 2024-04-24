package com.delvin.uywalkyp.RegisterSchema

data class RegisterRequest(
    val apellidos: String,
    val apodo: String,
    val celular: String,
    val direccion: String,
    val dni: String,
    val edad: Int,
    val email: String,
    val nombres: String,
    val password: String,
    val tipoUsuarioId: Int
)