package com.laurasando.juego_aprendix_mobile.data.models

data class Tematica(
    val contenido_tematica: String,
    val id_curso: String,
    val id_tematica: String,
    val nombre_tematica: String,
    val preguntas: List<PreguntaX>,
    val url: String
)