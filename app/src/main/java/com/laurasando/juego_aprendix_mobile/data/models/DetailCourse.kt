package com.laurasando.juego_aprendix_mobile.data.models

data class DetailCourse(
    val descripcion_curso: String,
    val duracion_curso: Int,
    val id_curso: Int,
    val nombre_curso: String,
    val tematicas: List<Tematica>,
    val url: String
)