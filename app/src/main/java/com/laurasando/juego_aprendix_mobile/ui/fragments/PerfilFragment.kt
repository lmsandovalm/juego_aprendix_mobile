package com.laurasando.juego_aprendix_mobile.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.data.models.ranking.UserScoreModel
import com.laurasando.juego_aprendix_mobile.data.models.user.DataUser
import com.laurasando.juego_aprendix_mobile.data.models.user.UserInfoResponseModel
import com.laurasando.juego_aprendix_mobile.data.network.FireStoreManager
import com.laurasando.juego_aprendix_mobile.databinding.FragmentCursosBinding
import com.laurasando.juego_aprendix_mobile.databinding.FragmentPerfilBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import showCustomSnackBar

class PerfilFragment : Fragment() {

    private lateinit var shrManager: SharePreferencesManager
    private lateinit var binding: FragmentPerfilBinding
    private lateinit var fsManager: FireStoreManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shrManager = SharePreferencesManager(requireContext())
        initUI()
    }

    private fun initUI() {
        initGetInfoUser()
        initGetLifeUser()
    }

    private fun initGetInfoUser() {
        val userId = shrManager.getPref("userId", "empty").toString()
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.getInfoUser(userId).enqueue(object : Callback<UserInfoResponseModel> {
            override fun onResponse(
                call: Call<UserInfoResponseModel>,
                response: Response<UserInfoResponseModel>
            ) {
                if (response.isSuccessful) {
                    initPrintDataUI(response.body()?.data)
                    initGetScoreUser(response.body()?.data?._id)
                } else {
                    binding.root.showCustomSnackBar("Algo salio mal", R.drawable.close)
                }
            }

            override fun onFailure(call: Call<UserInfoResponseModel>, t: Throwable) {
                binding.root.showCustomSnackBar("Algo salio mal", R.drawable.close)
            }

        })
    }

    private fun initGetScoreUser(id: String?) {
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.getUserScore(id!!).enqueue(object : Callback<UserScoreModel> {
            override fun onResponse(
                call: Call<UserScoreModel>,
                response: Response<UserScoreModel>
            ) {
                if (response.isSuccessful) {
                    binding.idTxtScore.text = response.body()?.data?.score.toString()
                }
            }

            override fun onFailure(call: Call<UserScoreModel>, t: Throwable) {
                binding.root.showCustomSnackBar("Algo salio mal", R.drawable.close)
            }

        })
    }

    private fun initGetLifeUser() {
        val userId = shrManager.getPref("userId", "empty").toString()

        fsManager.getLifeByUserId(userId) { data ->
            if (data != null) {
                binding.idTxtLife.text = data.life
            }
        }
    }

    private fun initPrintDataUI(user: DataUser?) {
        //Glide.with(binding.idImgProfile).load(user?.imageProfile).into(binding.idImgProfile)
        binding.idProfileName.text = user?.name
        binding.idEmailProfile.text = user?.email
    }
}