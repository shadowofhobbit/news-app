package test.task.newsapp.api

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("uc?id=1wozWr5swgtdV9PLyo2b09mtjaOD6sS2I")
    fun getNews(): Call<News>
}