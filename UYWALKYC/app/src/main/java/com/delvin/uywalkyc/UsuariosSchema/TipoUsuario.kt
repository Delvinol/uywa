package com.delvin.uywalkyc.UsuariosSchema

data class TipoUsuario(
    val createdAt: Long,
    val createdBy: Any,
    val descripcion: String,
    val estado: Int,
    val idTipoUsuario: Int,
    val nombreTipoUsuario: String,
    val updatedAt: Long,
    val updatedBy: Any
)