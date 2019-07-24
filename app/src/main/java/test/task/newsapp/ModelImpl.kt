package test.task.newsapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import test.task.newsapp.api.News
import test.task.newsapp.api.NewsService

class ModelImpl: NewsContract.Model {
    override fun getListOfNews(listener: NewsContract.Model.OnFinishedListener) {
        newsService.getNews().enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                listener.onGotNews(response.body()?.items ?: listOf())
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                listener.onFailure(t)
            }
        })
    }

    companion object {
        val newsService: NewsService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://drive.google.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                retrofit.create(NewsService::class.java)
        }
    }
}