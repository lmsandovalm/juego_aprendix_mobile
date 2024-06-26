package com.laurasando.juego_aprendix_mobile.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.models.ranking.DataX
import com.laurasando.juego_aprendix_mobile.data.models.ranking.RankingResponse
import com.laurasando.juego_aprendix_mobile.databinding.FragmentProgresoBinding
import com.laurasando.juego_aprendix_mobile.ui.Adapter.RankingAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProgresoFragment : Fragment() {

    private lateinit var binding: FragmentProgresoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgresoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIU()
    }

    private fun initIU() {
        initGetRanking()
    }

    private fun initGetRanking() {
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.getAllScore().enqueue(object : Callback<RankingResponse> {
            override fun onResponse(
                call: Call<RankingResponse>,
                response: Response<RankingResponse>
            ) {
                binding.idProgressRanking.visibility = View.GONE
                Log.e("LauraCursos", "onResponse: $response")
                if (response.isSuccessful) {
                    initReciclerView(response.body()?.data!!)
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                binding.idProgressRanking.visibility = View.GONE
                Log.e("detalle", "onFailure: ${t.message}")
                Toast.makeText(requireContext(), "Algo salio mal", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun initReciclerView(list: List<DataX>) {
        val rankingAdapter = RankingAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = rankingAdapter
    }

}