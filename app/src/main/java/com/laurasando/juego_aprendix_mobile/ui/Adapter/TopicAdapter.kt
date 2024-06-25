package com.laurasando.juego_aprendix_mobile.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.models.UserTopicCompleteResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.CourseTopic
import com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders.TopicViewHolder

class TopicAdapter(
    private val list: List<CourseTopic>,
    private val listTopicsCompleted: List<UserTopicCompleteResponse>,
) :
    RecyclerView.Adapter<TopicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.render(list[position], position, listTopicsCompleted)
    }

}