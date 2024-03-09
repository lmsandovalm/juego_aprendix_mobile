package com.laurasando.juego_aprendix_mobile.data.models

data class RegisterRequest(
    var name : String,
    var email : String,
    var password : String,
    var role : String
)
