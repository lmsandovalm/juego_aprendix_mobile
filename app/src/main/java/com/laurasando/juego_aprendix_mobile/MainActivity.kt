package com.laurasando.juego_aprendix_mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.data.models.LoginRequest
import com.laurasando.juego_aprendix_mobile.data.models.auth.LoginSuccessResponse
import com.laurasando.juego_aprendix_mobile.data.network.FireStoreManager
import com.laurasando.juego_aprendix_mobile.databinding.ActivityMainBinding
import com.laurasando.juego_aprendix_mobile.databinding.AlertsLoginBinding
import com.laurasando.juego_aprendix_mobile.navigation.MenuNavegacion
import com.laurasando.juego_aprendix_mobile.ui.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var fsManager: FireStoreManager
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

        }
        binding.btnResgistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlertSocialMedia(title: String, message: String, lottieAnim: Int) {
        val dialogSocialMedia =
            AlertsLoginBinding.inflate(LayoutInflater.from(this))
        val alertDialogSocial = AlertDialog.Builder(this).apply {
            setView(dialogSocialMedia.root)
            setCancelable(true)
        }.create()
        alertDialogSocial.dismiss()
        dialogSocialMedia.idTxtTitle.text = title
        dialogSocialMedia.idTxtMessage.text = message
        dialogSocialMedia.animLottie.setAnimation(lottieAnim)
        alertDialogSocial.window?.setBackgroundDrawableResource(R.color.transparente)

        alertDialogSocial.show()

    }


    private fun startLogin() {
        val email = binding.edtUsuarioLogin.text.toString()
        val password = binding.edtContrasenaLogin.text.toString()
        val userdata = LoginRequest(email, password)

        //validar los datos de ingreso
        binding.idProgressBarLogin.visibility = View.VISIBLE
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.signInUser(userdata).enqueue(object : Callback<LoginSuccessResponse?> {
            override fun onResponse(
                call: Call<LoginSuccessResponse?>,
                response: Response<LoginSuccessResponse?>
            ) {
                binding.idProgressBarLogin.visibility = View.GONE
                if (response.isSuccessful) {

                    val user = response.body()?.data?.user
                    val nombreUsuario = user?.name ?: "Usuario"
                    val correoUsuario = user?.email ?: "Correo"

                    fsManager = FireStoreManager()
                    fsManager.setInfoUserIfNoExists(response.body()?.data?.user!!._id) { res ->
                        if (res) {
                            shrManager.savePref("userId", response.body()?.data?.user!!._id)
                            shrManager.savePref("session_active", true)
                            shrManager.savePref("nameUser", nombreUsuario)
                            shrManager.savePref("emailUser", correoUsuario)
                            showAlertSocialMedia(
                                "Bienvenido!",
                                nombreUsuario,
                                R.raw.ok
                            )
                            Handler().postDelayed({
                                val intent = Intent(this@MainActivity, MenuNavegacion::class.java)
                                startActivity(intent)
                                finish()
                            }, 2480L)

                        } else {
                            showAlertSocialMedia(
                                "Error!",
                                "Ocurrio un error al iniciar sesión",
                                R.raw.errorlogin
                            )
                        }
                    }
                } else {
                    binding.idProgressBarLogin.visibility = View.GONE
                    showAlertSocialMedia(
                        "Error!",
                        "Usuario o ocntraseña invalidas.",
                        R.raw.errorlogin
                    )
                }
            }

            override fun onFailure(call: Call<LoginSuccessResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                binding.idProgressBarLogin.visibility = View.GONE
            }
        })
    }
}