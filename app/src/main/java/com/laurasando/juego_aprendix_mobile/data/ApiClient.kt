package com.laurasando.juego_aprendix_mobile.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        //cambio el parametro de la funcion por que va a ser siempre la misma url base
        fun retrofitHelper(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                // Añadir un interceptor para configurar el encabezado Content-Type como application/json
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://backend-final1.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}