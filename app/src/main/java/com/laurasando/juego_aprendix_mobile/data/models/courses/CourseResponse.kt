package com.laurasando.juego_aprendix_mobile.data.models.courses

data class CourseResponse(
    val data: List<DataCourse>,
    val message: String,
    val statusCode: Int,
    val totalResults: Int
)


