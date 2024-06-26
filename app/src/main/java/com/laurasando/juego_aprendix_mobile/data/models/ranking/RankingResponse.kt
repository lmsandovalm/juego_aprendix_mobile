package com.laurasando.juego_aprendix_mobile.data.models.ranking

data class RankingResponse(
    val data: List<DataX>,
    val message: String,
    val statusCode: Int,
    val totalResults: Int
)