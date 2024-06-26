package com.laurasando.juego_aprendix_mobile.data.models.user

data class UserInfoResponseModel(
    val data: DataUser,
    val message: String,
    val statusCode: Int,
    val totalResults: Int
)