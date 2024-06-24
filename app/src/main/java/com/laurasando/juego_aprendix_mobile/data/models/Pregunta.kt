package com.laurasando.juego_aprendix_mobile.data.models

data class Pregunta(
    val id: Int,
    val respuestas: List<Respuesta>,
    val tematica: Int,
    val texto_pregunta: String
)