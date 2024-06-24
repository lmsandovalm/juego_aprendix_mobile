package com.laurasando.juego_aprendix_mobile.data.models.courses

data class DataCourse(
    val _id: String,
    val coure_description: String,
    val coure_name: String,
    val course_topics: List<String>,
    val createdAt: String,
    val updatedAt: String
)