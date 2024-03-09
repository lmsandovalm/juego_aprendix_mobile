package com.laurasando.juego_aprendix_mobile.data.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthRemoteFirebase {

    fun registerUser(usuario: String, contrasena: String ):Boolean{
        var response = false
        val auth = FirebaseAuth.getInstance()
        val res=auth.createUserWithEmailAndPassword(usuario,contrasena)
            .addOnCompleteListener{task ->
                Log.e("TAG", "registerUser: ${task.result}", )
                response = task.isSuccessful
            }
        return response
    }
}