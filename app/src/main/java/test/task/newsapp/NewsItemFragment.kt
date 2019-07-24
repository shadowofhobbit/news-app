package test.task.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.marginTop
import com.squareup.picasso.Picasso
import test.task.newsapp.api.NewsItem
import kotlinx.android.synthetic.main.fragment_news_item.*
import kotlinx.android.synthetic.main.fragment_news_item.view.*
import kotlinx.android.synthetic.main.news_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

private const val NEWS_ITEM = "news"


class NewsItemFragment : Fragment() {

    private lateinit var newsItem: NewsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsItem = it.getParcelable(NEWS_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_item, container, false)
        view.imageView.contentDescription = newsItem.title
        Picasso.get().load(newsItem.picture).fit().into(view.imageView)
        view.titleView.text = newsItem.title
        val layoutParams = view.dateView.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin = resources.getDimensionPixelOffset(R.dimen.card_text_margin)
        view.dateView.layoutParams = layoutParams
        view.dateView.text = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale("ru"))
            .format(newsItem.date)
        view.contentView.text = newsItem.description
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        back.setOnClickListener{activity?.onBackPressed()}
    }


    companion object {
        @JvmStatic
        fun newInstance(newsItem: NewsItem) =
            NewsItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NEWS_ITEM, newsItem)
                }
            }
    }
}
