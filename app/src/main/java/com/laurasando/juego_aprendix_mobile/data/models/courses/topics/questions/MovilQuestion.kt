package com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions

data class MovilQuestion(
    val _id: String,
    val createdAt: String,
    val question_answers: List<QuestionAnswer>,
    val question_text: String,
    val description_text: String,
    val topic: String,
    val updatedAt: String
)