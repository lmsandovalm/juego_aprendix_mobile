package com.laurasando.juego_aprendix_mobile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.models.RegisterRequest
import com.laurasando.juego_aprendix_mobile.data.models.UserModel
import com.laurasando.juego_aprendix_mobile.data.models.UserResponse
import com.laurasando.juego_aprendix_mobile.data.network.AuthRemoteFirebase
import com.laurasando.juego_aprendix_mobile.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var authRemoteFirebase: AuthRemoteFirebase
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initUI()

    }

    private fun startRegister() {

        val name = binding.edtUsuarioRegistro.text.toString()
        val email = binding.edtEmailRegistro.text.toString()
        val password = binding.edtContrasenaRegistro.text.toString()
        val role = "admin" //cambiar depues a un rol por defecto diferente a admin
        val requestBody = RegisterRequest(name, email, password, role)
        //llamamos al servicio que vamos a consumir ('registerNewUser')
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.registerNewUser(requestBody)?.enqueue(object : Callback<UserModel?> {
            override fun onResponse(call: Call<UserModel?>, response: Response<UserModel?>) {
                Log.e("Laura", "onResponse: $response")
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Bienvenido ${response.body()?.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Log.e("Laura", "onResponse: ${response.errorBody()} ", )
                    Toast.makeText(this@RegisterActivity, "LLene todos los datos correctamente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserModel?>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Laura", "fallo: ${t.message}")
            }


        })

    }

    private fun initUI() {
        setUpClickListener()
    }


    private fun setUpClickListener() {
        binding.btnRegistro.setOnClickListener { startRegister() }
    }
}