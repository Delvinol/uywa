package com.delvin.uywalkyc.LoginSchema

data class LoginResponse(
    val apellidos: String,
    val id: Int,
    val idTipoUsuario: Int,
    val nombres: String,
    val token: String,
    val idPropietario: Int,
)