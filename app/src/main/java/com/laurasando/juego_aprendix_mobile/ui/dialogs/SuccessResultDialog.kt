package com.laurasando.juego_aprendix_mobile.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.data.models.ranking.UserScoreModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuccessResultDialog : DialogFragment() {
    private var scoreUser = "0"
    private lateinit var sharedPrefs: SharePreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar)
        sharedPrefs = SharePreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        return inflater.inflate(R.layout.pantallafinal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initial()
        val btnContinue = view.findViewById<Button>(R.id.idBtnContinue)
        btnContinue.setOnClickListener {
            findNavController().navigateUp()
            dismiss()
        }
    }

    private fun initial() {
        initGetUserScore()
    }

    private fun initGetUserScore() {
        val userId = sharedPrefs.getPref("userId", "empty").toString()
        val apiServices: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiServices.getUserScore(userId).enqueue(object : Callback<UserScoreModel> {
            override fun onResponse(
                call: Call<UserScoreModel>,
                response: Response<UserScoreModel>
            ) {
                view?.findViewById<ProgressBar>(R.id.idProgressBarDialog)?.visibility = View.GONE
                if (response.isSuccessful) {
                    scoreUser = response.body()?.data?.score.toString()
                    view?.findViewById<TextView>(R.id.score_final)?.text = scoreUser
                }
            }

            override fun onFailure(call: Call<UserScoreModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Algo salio mal", Toast.LENGTH_SHORT).show()
                view?.findViewById<ProgressBar>(R.id.idProgressBarDialog)?.visibility = View.GONE
            }

        })
    }
}