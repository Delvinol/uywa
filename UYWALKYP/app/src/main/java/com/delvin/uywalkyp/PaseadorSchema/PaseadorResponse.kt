package com.delvin.uywalkyp.PaseadorSchema

data class PaseadorResponse(
    val calificacion: Int,
    val descripcion: String,
    val disponibilidad: Boolean,
    val experiencia: String,
    val idCategoria: Int,
    val idPaseador: Int,
    val idTipoUsuario: Int,
    val idUsuario: Int,
    val nombres: String,
    val saldo: Int,
    val tarifa: Double,
    val ubicacion: String
)