package com.laurasando.juego_aprendix_mobile.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.data.models.UserTopicCompleteResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.DetailCourse
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.CourseTopic
import com.laurasando.juego_aprendix_mobile.data.network.FireStoreManager
import com.laurasando.juego_aprendix_mobile.databinding.FragmentCourseTopicsBinding
import com.laurasando.juego_aprendix_mobile.ui.Adapter.TopicAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class CourseTopicsFragment : Fragment() {
    private lateinit var binding: FragmentCourseTopicsBinding
    private lateinit var fsManager: FireStoreManager
    private lateinit var sharedPrefs: SharePreferencesManager
    private var idCourseParam = ""
    private val args: CourseTopicsFragmentArgs by navArgs()
    private lateinit var userTopicsCompleted: List<UserTopicCompleteResponse>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idCourseParam = args.keyIdCourse
        sharedPrefs = SharePreferencesManager(requireContext())
        initIU()
    }

    private fun initIU() {
        initGetAllTopics()
        initGetUserTopicCompleted()
    }

    private fun initGetUserTopicCompleted() {
        userTopicsCompleted = arrayListOf()
        fsManager = FireStoreManager()
        val userId = sharedPrefs.getPref("userId", "empty").toString()
        fsManager.getQuestionComplete(userId) { res ->
            if (res != null) {
                userTopicsCompleted = res
            }
        }
    }

    private fun initGetAllTopics() {

        val apiServices: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiServices.getDetailCourse(idCourseParam).enqueue(object : Callback<DetailCourse> {
            override fun onResponse(call: Call<DetailCourse>, response: Response<DetailCourse>) {
                binding.idProgressTopics.visibility = View.GONE
                if (response.isSuccessful) {
                    initRecyclerView(response.body()?.data!!.course_topics)
                }
            }

            override fun onFailure(call: Call<DetailCourse>, t: Throwable) {
                binding.idProgressTopics.visibility = View.GONE
                Toast.makeText(requireContext(), "Algo salio mal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initRecyclerView(list: List<CourseTopic>) {
        val topicAdapter = TopicAdapter(list, userTopicsCompleted)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = topicAdapter
    }
}