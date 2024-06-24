package com.laurasando.juego_aprendix_mobile.data.models.user

data class UserLifeModel(
    var userId: String? = null,
    var life: String = "5",
    var timeStamps: MutableList<Long> = mutableListOf()
)

