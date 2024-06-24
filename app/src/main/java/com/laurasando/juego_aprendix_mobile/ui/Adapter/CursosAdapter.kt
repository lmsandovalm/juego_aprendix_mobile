package com.laurasando.juego_aprendix_mobile.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.models.CursosResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.DataCourse
import com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders.CursosViewHolder

class CursosAdapter(private  val list : List<DataCourse>) : RecyclerView.Adapter<CursosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursosViewHolder {
        return CursosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.curso_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CursosViewHolder, position: Int) {
        holder.render(list[position])
    }

}