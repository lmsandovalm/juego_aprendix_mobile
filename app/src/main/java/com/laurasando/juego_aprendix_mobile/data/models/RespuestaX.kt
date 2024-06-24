package com.laurasando.juego_aprendix_mobile.data.models

data class RespuestaX(
    val es_correcta: Boolean,
    val id: Int,
    val pregunta: Int,
    val texto_respuesta: String
)