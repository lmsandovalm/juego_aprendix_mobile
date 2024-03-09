package com.laurasando.juego_aprendix_mobile.data.interfaces

import com.laurasando.juego_aprendix_mobile.data.models.LoginRequest
import com.laurasando.juego_aprendix_mobile.data.models.RegisterRequest
import com.laurasando.juego_aprendix_mobile.data.models.UserModel
import com.laurasando.juego_aprendix_mobile.data.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("api-auth/login/")
    fun signInUser(@Body loginRequest: LoginRequest): Call<UserResponse?>

    @POST("api-auth/register/")
    fun registerNewUser(@Body registerRequest: RegisterRequest?): Call<UserModel?>?

}