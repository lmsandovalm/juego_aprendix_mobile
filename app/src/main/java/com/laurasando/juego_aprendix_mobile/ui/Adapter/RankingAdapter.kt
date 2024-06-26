package com.laurasando.juego_aprendix_mobile.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.models.ranking.DataX
import com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders.RankingViewHolder

class RankingAdapter(private val list: List<DataX>) : RecyclerView.Adapter<RankingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ranking_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.render(list[position])
    }

}