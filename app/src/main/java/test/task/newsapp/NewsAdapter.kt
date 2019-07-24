package test.task.newsapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_layout.view.*
import test.task.newsapp.NewsFragment.OnNewsFragmentInteractionListener
import test.task.newsapp.api.NewsItem
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapter(private val listener: OnNewsFragmentInteractionListener?
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var news: MutableList<NewsItem> = arrayListOf()
    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsItem
            listener?.onNewsItemSelected(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = news[position]

        holder.titleView.text = newsItem.title
        holder.dateView.text = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale("ru"))
            .format(newsItem.date)
        holder.mContentView.text = newsItem.description

        with(holder.mView) {
            tag = newsItem
            setOnClickListener(onClickListener)
        }
        holder.imageView.contentDescription = newsItem.title
        Picasso.get().load(newsItem.picture).fit().into(holder.imageView)
    }

    override fun getItemCount(): Int = news.size

    fun setData(news: List<NewsItem>) {
        this.news.clear()
        this.news.addAll(news)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val titleView: TextView = mView.titleView
        val dateView: TextView = mView.dateView
        val imageView: ImageView = mView.imageView
        val mContentView: TextView = mView.contentView

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
