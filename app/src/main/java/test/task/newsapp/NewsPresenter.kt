package test.task.newsapp

import test.task.newsapp.api.NewsItem


class NewsPresenter(var model: NewsContract.Model, var view: NewsContract.View?) : NewsContract.Presenter,
    NewsContract.Model.OnFinishedListener {

    override fun refreshNews() {
        view?.let {

            if (it.isConnectedToNetwork()) {
                it.showProgress()
                model.getListOfNews(this)
            } else {
                it.hideProgress()
                it.displayNoInternet()
            }
        }
    }

    override fun dontNeedNews() {
        view?.hideProgress()
    }

    override fun onGotNews(news: List<NewsItem>) {
        view?.hideProgress()
        view?.displayNews(news)
    }

    override fun onFailure(t: Throwable) {
        view?.hideProgress()
        view?.displayError()
    }

    override fun detachView() {
        view = null
    }
}