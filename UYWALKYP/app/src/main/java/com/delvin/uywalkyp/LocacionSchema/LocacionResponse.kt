package com.delvin.uywalkyp.LocacionSchema

data class LocacionResponse(
    val idPaseador: Int,
    val idTipoUsuario: Int,
    val idUsuario: Int,
    val latitud: Double,
    val longitud: Double,
    val nombres: String,
    val apellidos: String,
    val idLocacionPaseador: Int,
)