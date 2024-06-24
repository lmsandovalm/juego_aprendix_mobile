package com.laurasando.juego_aprendix_mobile.data.models.errors

data class ErrorsValidation(
    val errors: List<String>,
    val message: String,
    val statusCode: Int
)