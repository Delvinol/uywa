package com.delvin.uywalkyc.PropietarioSchema

data class PropietarioRequest(
    val calificacion: Int,
    val comentario: String,
    val disponibilidad: Boolean,
    val idUsuario: Int,
    val preferenciasPaseo: String,
    val saldo: Int,
    val ubicacion: String
)