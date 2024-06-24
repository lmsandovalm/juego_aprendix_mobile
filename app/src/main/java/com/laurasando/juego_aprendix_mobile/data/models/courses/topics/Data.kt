package com.laurasando.juego_aprendix_mobile.data.models.courses.topics

data class Data(
    val _id: String,
    val coure_description: String,
    val coure_name: String,
    val course_topics: List<CourseTopic>,
    val createdAt: String,
    val updatedAt: String
)