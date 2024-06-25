package com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders

import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.models.UserTopicCompleteResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.CourseTopic
import com.laurasando.juego_aprendix_mobile.databinding.TopicItemBinding
import com.laurasando.juego_aprendix_mobile.ui.fragments.CourseTopicsFragmentDirections

class TopicViewHolder(val inflate: View) : RecyclerView.ViewHolder(inflate) {

    private val binding = TopicItemBinding.bind(inflate)

    fun render(
        topicResponse: CourseTopic,
        position: Int,
        listTopicsCompleted: List<UserTopicCompleteResponse>
    ) {
        binding.txtDescription.text = topicResponse.topic_description
        binding.txtTitle.text = topicResponse.topic_name

        // Determinar si el tema está completado
        val isCompleted = listTopicsCompleted.any { it.topicId == topicResponse._id }

        // Obtener el índice del último tema completado
        val lastCompletedIndex = listTopicsCompleted
            .map { it.topicId }
            .let { completedIds ->
                topicResponse._id.let { id -> completedIds.indexOfLast { it == id } }
            }

        // Verificar si el tema actual es el siguiente a completar
        val isNextToComplete = when {
            listTopicsCompleted.isEmpty() -> position == 0
            else -> position == listTopicsCompleted.size
        }

        // Configurar el fondo y el OnClickListener basado en la condición
        when {
            isCompleted -> {
                binding.idItemTopic.setBackgroundColor(inflate.context.getColor(R.color.azul_borde))
                binding.idItemTopic.setOnClickListener {
                    inflate.findNavController().navigate(
                        CourseTopicsFragmentDirections
                            .actionCourseTopicsFragmentToQuestionsTopicFragment(
                                keyIdTopic = topicResponse._id,
                                isCompleted = true
                            )
                    )
                }
            }

            isNextToComplete -> {
                binding.idItemTopic.setBackgroundColor(inflate.context.getColor(R.color.gris))
                binding.idItemTopic.setOnClickListener {
                    inflate.findNavController().navigate(
                        CourseTopicsFragmentDirections
                            .actionCourseTopicsFragmentToQuestionsTopicFragment(
                                keyIdTopic = topicResponse._id,
                                isCompleted = false
                            )
                    )
                }
            }

            else -> {
                binding.idItemTopic.setOnClickListener {
                    Toast.makeText(
                        inflate.context,
                        "No se puede porque debe terminar la anterior temática",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
