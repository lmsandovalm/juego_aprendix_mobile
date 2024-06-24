package com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.CourseTopic
import com.laurasando.juego_aprendix_mobile.databinding.TopicItemBinding
import com.laurasando.juego_aprendix_mobile.ui.fragments.CourseTopicsFragmentDirections

class TopicViewHolder(val inflate: View) : RecyclerView.ViewHolder(inflate) {

    private val binding = TopicItemBinding.bind(inflate)
    fun render(topicResponse: CourseTopic) {
        binding.txtDescription.text = topicResponse.topic_description
        binding.txtTitle.text = topicResponse.topic_name

        binding.idItemTopic.setOnClickListener {

            inflate
                .findNavController()
                .navigate(
                    CourseTopicsFragmentDirections
                        .actionCourseTopicsFragmentToQuestionsTopicFragment(keyIdTopic = topicResponse._id)
                )

        }

    }
}