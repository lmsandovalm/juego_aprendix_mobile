package com.laurasando.juego_aprendix_mobile.data.models

data class UserModel(

    val id: Int,
    val email: String,
    val groups: List<Int>,
    val is_active: Boolean,
    val is_staff: Boolean,
    val is_superuser: Boolean,
    val last_login: Any,
    val password: String,
    val name: String,
    val role: String,
    val user_permissions: List<Any>

)
