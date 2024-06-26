package com.laurasando.juego_aprendix_mobile.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.models.CursosResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.CourseResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.DataCourse
import com.laurasando.juego_aprendix_mobile.databinding.FragmentCursosBinding
import com.laurasando.juego_aprendix_mobile.ui.Adapter.CursosAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CursosFragment : Fragment() {

    private lateinit var binding: FragmentCursosBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCursosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initIU()
    }

    private fun initIU() {
        initGetCourses()
    }

    private fun initGetCourses() {
        val apiService: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiService.getAllCourses().enqueue(object : Callback<CourseResponse> {
            override fun onResponse(
                call: Call<CourseResponse>,
                response: Response<CourseResponse>
            ) {
                binding.idProgressCourses.visibility = View.GONE
                Log.e("LauraCursos", "onResponse: $response")
                if (response.isSuccessful) {
                    initReciclerView(response.body()?.data!!)
                }
            }

            override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                binding.idProgressCourses.visibility = View.GONE
                Log.e("detalle", "onFailure: ${t.message}")
                Toast.makeText(requireContext(), "Algo salio mal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initReciclerView(list: List<DataCourse>) {
        val courseAdapter = CursosAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = courseAdapter
    }
}
