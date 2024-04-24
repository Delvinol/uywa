package com.delvin.uywalkyc.PropietarioSchema

data class PropietarioResponse(
    val calificacion: Int,
    val comentario: String,
    val disponibilidad: Boolean,
    val idPropietario: Int,
    val idTipoUsuario: Int,
    val idUsuario: Int,
    val nombres: String,
    val preferenciasPaseo: String,
    val saldo: Int,
    val ubicacion: String
)