package com.laurasando.juego_aprendix_mobile.data.models

data class RegisterRequest(
    var name : String,
    var email : String,
    var password : String,
    var lastname : String = "No data",
    var confirmPassword : String = password,


)
