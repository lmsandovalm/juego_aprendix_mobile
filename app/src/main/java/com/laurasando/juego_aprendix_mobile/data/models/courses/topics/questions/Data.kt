package com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions

data class Data(
    val _id: String,
    val course: String,
    val createdAt: String,
    val movil_questions: List<MovilQuestion>,
    val topic_description: String,
    val topic_material: List<String>,
    val topic_name: String,
    val updatedAt: String
)