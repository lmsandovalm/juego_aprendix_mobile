package com.laurasando.juego_aprendix_mobile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.laurasando.juego_aprendix_mobile.MainActivity
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.models.RegisterRequest
import com.laurasando.juego_aprendix_mobile.data.models.auth.RegisterSuccessResponse
import com.laurasando.juego_aprendix_mobile.data.models.errors.ErrorsValidation
import com.laurasando.juego_aprendix_mobile.data.network.AuthRemoteFirebase
import com.laurasando.juego_aprendix_mobile.data.network.FireStoreManager
import com.laurasando.juego_aprendix_mobile.databinding.ActivityRegisterBinding
import com.laurasando.juego_aprendix_mobile.databinding.AlertsRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var authRemoteFirebase: AuthRemoteFirebase
    lateinit var fsManager: FireStoreManager
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initUI()

    }


    private fun showAlertSocialMedia() {
        val dialogSocialMedia =
            AlertsRegisterBinding.inflate(LayoutInflater.from(this))
        val alertDialogSocial = AlertDialog.Builder(this).apply {
            setView(dialogSocialMedia.root)
            setCancelable(true)
        }.create()
        alertDialogSocial.dismiss()

        alertDialogSocial.window?.setBackgroundDrawableResource(R.color.transparente)

        alertDialogSocial.show()

    }

    private fun startRegister() {

        val name = binding.edtUsuarioRegistro.text.toString()
        val email = binding.edtEmailRegistro.text.toString()
        val password = binding.edtContrasenaRegistro.text.toString()
        val requestBody = RegisterRequest(name, email, password)

        //llamamos al servicio que vamos a consumir ('registerNewUser')
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.registerNewUser(requestBody)
            ?.enqueue(object : Callback<RegisterSuccessResponse?> {
                override fun onResponse(
                    call: Call<RegisterSuccessResponse?>,
                    response: Response<RegisterSuccessResponse?>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Bienvenido ${response.body()?.data?.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                        fsManager = FireStoreManager()
                        fsManager.setInfoUser(response.body()?.data!!._id)
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorsValidation::class.java)
                        Toast.makeText(
                            this@RegisterActivity,
                            "El usuario ya se encuentra registrado",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                override fun onFailure(call: Call<RegisterSuccessResponse?>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LauraFallo", "fallo: ${t.message}")
                }


            })

    }

    private fun initUI() {
        setUpClickListener()
        showAlertSocialMedia()
    }


    private fun setUpClickListener() {
        binding.btnRegistro.setOnClickListener { startRegister() }
    }
}