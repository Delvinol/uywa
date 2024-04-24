package com.delvin.uywalkyp.PaseadorSchema

data class PaseadorRequest(
    val calificacion: Int,
    val descripcion: String,
    val disponibilidad: Boolean,
    val experiencia: String,
    val idCategoria: Int,
    val idUsuario: Int,
    val saldo: Int,
    val tarifa: Double,
    val ubicacion: String
)