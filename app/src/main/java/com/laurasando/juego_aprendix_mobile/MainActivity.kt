package com.laurasando.juego_aprendix_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.models.LoginRequest
import com.laurasando.juego_aprendix_mobile.data.models.UserResponse
import com.laurasando.juego_aprendix_mobile.databinding.ActivityMainBinding
import com.laurasando.juego_aprendix_mobile.ui.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginRequest: LoginRequest
    private lateinit var loginResponse: UserResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initIU()
    }

    private fun initIU() {
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.btnIngresar.setOnClickListener {
            register()
            startLogin()

        }
    }

    private fun register() {
        binding.btnResgistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startLogin() {
        val email = binding.edtUsuarioLogin.text.toString()
        val password = binding.edtContrasenaLogin.text.toString()
        val userdata = LoginRequest(email, password)

        //validar los datos de ingreso


        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.signInUser(userdata).enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                Log.e("Laura", "onResponse: $response")
                Toast.makeText(this@MainActivity, "${response.body()?.user?.name}", Toast.LENGTH_SHORT).show()
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Bienvenido ${response.body()?.user?.name}",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(this@MainActivity, "Credenciales invalidas", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Laura", "fallo: ${t.message}")
            }


        })
    }
}