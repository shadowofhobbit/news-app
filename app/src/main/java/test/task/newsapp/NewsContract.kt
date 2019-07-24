package test.task.newsapp

import test.task.newsapp.api.NewsItem

interface NewsContract {

    interface View {
        fun displayNews(news: List<NewsItem>)
        fun showProgress()
        fun hideProgress()
        fun displayError()
        fun displayNoInternet()
        fun isConnectedToNetwork(): Boolean
    }

    interface Presenter {
        fun refreshNews()
        fun dontNeedNews()
        fun detachView()
    }

    interface Model {
        fun getListOfNews(listener: OnFinishedListener)

        interface OnFinishedListener {
            fun onGotNews(news: List<NewsItem>)
            fun onFailure(t: Throwable)
        }
    }


}