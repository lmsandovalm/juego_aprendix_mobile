package com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions

data class QuestionAnswer(
    val _id: String,
    val answer: String,
    val createdAt: String,
    val is_correct: Boolean,
    val question: String,
    val updatedAt: String
)