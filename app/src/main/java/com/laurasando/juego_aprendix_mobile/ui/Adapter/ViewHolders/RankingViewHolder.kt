package com.laurasando.juego_aprendix_mobile.ui.Adapter.ViewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.models.ranking.DataX
import com.laurasando.juego_aprendix_mobile.databinding.RankingItemBinding
import kotlin.random.Random

class RankingViewHolder(val inflate: View) : RecyclerView.ViewHolder(inflate) {

    private val binding = RankingItemBinding.bind(inflate)
    private val arrayImages = arrayListOf<Int>(
        R.drawable.img0,
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img7,
        R.drawable.img8,
        R.drawable.img9,
    )
    fun render(rankingResponse: DataX) {

        binding.txtScore.text = rankingResponse.score.toString()
        binding.txtNameUsuario.text = rankingResponse.user.name

        val randomNumber = Random.nextInt(0, 10)
        val imageRandom = arrayImages[randomNumber]
        binding.imgUser.setImageResource(imageRandom) //agregado

        /* binding.idItemRanking.setOnClickListener {
            inflate
                .findNavController()
                .navigate(
                    ProgresoFragment.actionNavCursosToCourseTopicsFragment(
                        keyIdRanking = rankingResponse._id
                    )
                )
        }*/
    }
}