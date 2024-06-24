package com.laurasando.juego_aprendix_mobile.data.models.auth

data class User(
    val _id: String,
    val createdAt: String,
    val email: String,
    val lastname: String,
    val name: String,
    val password: String,
    val role: Role,
    val updatedAt: String
)