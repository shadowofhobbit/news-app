package test.task.newsapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.view.*
import test.task.newsapp.NewsContract.Presenter
import test.task.newsapp.api.NewsItem



class NewsFragment : Fragment(), NewsContract.View {

    private lateinit var presenter: Presenter
    private var listener: OnNewsFragmentInteractionListener? = null
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        newsAdapter = NewsAdapter(listener)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = newsAdapter
        presenter = NewsPresenter(ModelImpl(), this)
        return view
    }

    private fun showOrHideRecyclerView() {
        if (newsAdapter.itemCount == 0) {
            recyclerView.visibility = View.INVISIBLE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.INVISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnNewsFragmentInteractionListener")
        }
        newsViewModel = ViewModelProviders.of(activity!!).get(NewsViewModel::class.java)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefresh.setOnRefreshListener { presenter.refreshNews() }
        newsViewModel.getData(presenter)
            .observe(this,
                Observer<List<NewsItem>> { news ->
                    if (news != null) {
                        newsAdapter.setData(news)
                        showOrHideRecyclerView()
                    }
                })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnNewsFragmentInteractionListener {
        fun onNewsItemSelected(item: NewsItem)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
                arguments = Bundle()
            }
    }

    override fun displayNews(news: List<NewsItem>) {
        newsViewModel.setData(news)
        newsAdapter.setData(news)
        showOrHideRecyclerView()
    }

    override fun showProgress() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefresh.isRefreshing = false
    }

    override fun displayError() {
    }

    override fun displayNoInternet() {
        newsAdapter.setData(arrayListOf())
        showOrHideRecyclerView()
    }

    override fun isConnectedToNetwork(): Boolean {
        val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
                as? ConnectivityManager
        var networkInfo: NetworkInfo? = null
        if (manager != null) {
            networkInfo = manager.activeNetworkInfo
        }
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}

class NewsViewModel : ViewModel() {
    private var news: MutableLiveData<List<NewsItem>>? = null

    fun getData(presenter: Presenter): LiveData<List<NewsItem>> {

        if (news == null) {
            news = MutableLiveData()
            presenter.refreshNews()
        } else {
            presenter.dontNeedNews()
        }
        return news!!
    }

    fun setData(songs: List<NewsItem>) {
        this.news?.value = songs
    }
}