package com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.data.models.CursosResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.DataCourse
import com.laurasando.juego_aprendix_mobile.databinding.CursoItemBinding
import com.laurasando.juego_aprendix_mobile.ui.fragments.CursosFragmentDirections

class CursosViewHolder(val inflate: View) : RecyclerView.ViewHolder(inflate) {

    private val binding = CursoItemBinding.bind(inflate)
    fun render(cursosResponse: DataCourse) {

        binding.txtDescription.text = cursosResponse.coure_description
        binding.txtTitle.text = cursosResponse.coure_name
        binding.idItemCourse.setOnClickListener {
            inflate
                .findNavController()
                .navigate(
                    CursosFragmentDirections.actionNavCursosToCourseTopicsFragment(
                        keyIdCourse = cursosResponse._id
                    )
                )
        }
    }
}