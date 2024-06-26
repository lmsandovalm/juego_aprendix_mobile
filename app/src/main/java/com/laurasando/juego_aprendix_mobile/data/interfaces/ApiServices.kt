package com.laurasando.juego_aprendix_mobile.data.interfaces


import com.laurasando.juego_aprendix_mobile.data.models.LoginRequest
import com.laurasando.juego_aprendix_mobile.data.models.RegisterRequest
import com.laurasando.juego_aprendix_mobile.data.models.TopicResponse
import com.laurasando.juego_aprendix_mobile.data.models.auth.LoginSuccessResponse
import com.laurasando.juego_aprendix_mobile.data.models.auth.RegisterSuccessResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.CourseResponse
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions.QuestionsTopicResponse
import com.laurasando.juego_aprendix_mobile.data.models.ranking.RankingResponse
import com.laurasando.juego_aprendix_mobile.data.models.ranking.UserScoreModel
import com.laurasando.juego_aprendix_mobile.data.models.ranking.request.AddPointsRequest
import com.laurasando.juego_aprendix_mobile.data.models.user.UserInfoResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    /**
     *Autenticacion
     */
    @POST("auth/login")
    fun signInUser(@Body loginRequest: LoginRequest): Call<LoginSuccessResponse?>

    @POST("auth/register")
    fun registerNewUser(@Body registerRequest: RegisterRequest?): Call<RegisterSuccessResponse?>?
    /*
        @POST("auth/logout")
        fun logoutUser(@Header("Authorization") token: String): Call<LogoutResponse?>
        */
    /**
     *Cursos
     */

    @GET("courses")
    fun getAllCourses(): Call<CourseResponse>

    @GET("courses/find/{id}")
    fun getDetailCourse(@Path("id") id: String): Call<com.laurasando.juego_aprendix_mobile.data.models.courses.topics.DetailCourse>

    /**
     *Tematicas
     */

    @GET("movil/findTopicByIdWithQuestions/{id}")
    fun getDetailTopic(@Path("id") id: String): Call<QuestionsTopicResponse>

    //RANKING
    @POST("ranking/add-points")
    fun addPoints(@Body registerRequest: AddPointsRequest): Call<UserScoreModel?>

    @GET("ranking/user-score/{id}")
    fun getUserScore(@Path("id") id: String): Call<UserScoreModel>

    @GET("ranking/all-user-scores")
    fun getAllScore(): Call<RankingResponse>


    /**
     * Users
     * */

    @GET("users/find/{id}")
    fun getInfoUser(@Path("id") id: String): Call<UserInfoResponseModel>
}