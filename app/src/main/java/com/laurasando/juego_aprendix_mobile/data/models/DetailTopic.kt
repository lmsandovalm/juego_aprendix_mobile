package com.laurasando.juego_aprendix_mobile.data.models

data class DetailTopic(
    val contenido_tematica: String,
    val id_curso: String,
    val nombre_tematica: String,
    val preguntas: List<Pregunta>,
    val url: String
)