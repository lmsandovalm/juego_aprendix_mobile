package com.laurasando.juego_aprendix_mobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.DetailCourse
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.CourseTopic
import com.laurasando.juego_aprendix_mobile.databinding.FragmentCourseTopicsBinding
import com.laurasando.juego_aprendix_mobile.ui.Adapter.TopicAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CourseTopicsFragment : Fragment() {
    private lateinit var binding: FragmentCourseTopicsBinding
    var idCourseParam = ""
    val args: CourseTopicsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idCourseParam = args.keyIdCourse
        initIU()
    }

    private fun initIU() {
        initGetAllTopics()
    }

    private fun initGetAllTopics() {

        val apiServices: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiServices.getDetailCourse(idCourseParam).enqueue(object : Callback<DetailCourse> {
            override fun onResponse(call: Call<DetailCourse>, response: Response<DetailCourse>) {
                binding.idProgressTopics.visibility = View.GONE
                if (response.isSuccessful) {
                    initReciclerView(response.body()?.data!!.course_topics)
                }
            }

            override fun onFailure(call: Call<DetailCourse>, t: Throwable) {
                binding.idProgressTopics.visibility = View.GONE
                Toast.makeText(requireContext(), "Algo salio mal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initReciclerView(list: List<CourseTopic>) {
        val topicAdapter = TopicAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = topicAdapter
    }
}