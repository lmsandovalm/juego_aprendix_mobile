package com.laurasando.juego_aprendix_mobile.data.models

data class PreguntaX(
    val id: Int,
    val respuestas: List<RespuestaX>,
    val tematica: Int,
    val texto_pregunta: String
)