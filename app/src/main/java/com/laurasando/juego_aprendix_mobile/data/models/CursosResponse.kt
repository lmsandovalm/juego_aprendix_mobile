package com.laurasando.juego_aprendix_mobile.data.models

data class CursosResponse(

    val url: String,
    val id_curso: Int,
    val tematicas: List<Tematica>,
    val nombre_curso: String,
    val descripcion_curso: String,
    val duracion_curso: Int,
)