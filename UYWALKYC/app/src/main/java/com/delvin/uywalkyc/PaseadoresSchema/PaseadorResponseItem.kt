package com.delvin.uywalkyc.PaseadoresSchema

data class PaseadorResponseItem(
    val apellidos: String,
    val calificacion: Int,
    val categoriaNombre: String,
    val descripcion: String,
    val experiencia: String,
    val nombres: String,
    val saldo: Double,
    val tarifa: Double,
    val ubicacion: String,
    val email:String,
    val dni:String,
    val celular:String,
    val latitud:String,
    val longitud:String
)