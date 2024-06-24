package com.laurasando.juego_aprendix_mobile.data.models.auth

data class RegisterSuccessResponse (
    val data: UserRegister,
    val message: String,
    val statusCode: Int,
    val totalResults: Int,
    var errors:ErrorResponse
)