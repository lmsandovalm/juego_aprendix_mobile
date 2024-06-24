package com.laurasando.juego_aprendix_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.data.models.LoginRequest
import com.laurasando.juego_aprendix_mobile.data.models.UserModel
import com.laurasando.juego_aprendix_mobile.data.models.UserResponse
import com.laurasando.juego_aprendix_mobile.data.models.auth.LoginSuccessResponse
import com.laurasando.juego_aprendix_mobile.databinding.ActivityMainBinding
import com.laurasando.juego_aprendix_mobile.databinding.AlertsLoginBinding
import com.laurasando.juego_aprendix_mobile.navigation.MenuNavegacion
import com.laurasando.juego_aprendix_mobile.ui.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var shrManager: SharePreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        shrManager = SharePreferencesManager(this)
        initIU()
    }

    private fun initIU() {
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.btnIngresar.setOnClickListener {

            startLogin()
            showAlertSocialMedia()
        }
        binding.btnResgistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertSocialMedia() {
        val dialogSocialMedia =
            AlertsLoginBinding.inflate(LayoutInflater.from(this))
        val alertDialogSocial = AlertDialog.Builder(this).apply {
            setView(dialogSocialMedia.root)
            setCancelable(true)
        }.create()
        alertDialogSocial.dismiss()

        alertDialogSocial.window?.setBackgroundDrawableResource(R.color.transparente)

        alertDialogSocial.show()

    }


    private fun startLogin() {
        val email = binding.edtUsuarioLogin.text.toString()
        val password = binding.edtContrasenaLogin.text.toString()
        val userdata = LoginRequest(email, password)

        //validar los datos de ingreso

        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.signInUser(userdata).enqueue(object : Callback<LoginSuccessResponse?> {
            override fun onResponse(
                call: Call<LoginSuccessResponse?>,
                response: Response<LoginSuccessResponse?>
            ) {
                Log.e("Laura", "onResponse: $response")
                Toast.makeText(
                    this@MainActivity,
                    "${response.body()?.data?.user?.name}",
                    Toast.LENGTH_SHORT
                ).show()
                if (response.isSuccessful) {
                    val user = response.body()?.data?.user
                    val nombreUsuario = user?.name ?: "Usuario"
                    Toast.makeText(
                        this@MainActivity,
                        "Bienvenido $nombreUsuario",
                        Toast.LENGTH_SHORT
                    ).show()
                    shrManager.savePref("userId", response.body()?.data?.user!!._id)
                    shrManager.savePref("session_active", true)
                    val intent = Intent(this@MainActivity, MenuNavegacion::class.java)

                    intent.putExtra("nombreUsuario", user?.name)
                    intent.putExtra("correoUsuario", user?.email)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(this@MainActivity, "Credenciales invalidas", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginSuccessResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Laura", "fallo: ${t.message}")
            }


        })
    }
}