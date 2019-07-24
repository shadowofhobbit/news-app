package test.task.newsapp.api

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import java.util.*


data class News(@JsonProperty("News") val items: List<NewsItem>)

@Parcelize
data class NewsItem(
    @JsonProperty("tittle")
    val title: String,
    @JsonProperty("desc")
    val description: String,
    @JsonProperty("date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    val date: Date,
    @JsonProperty("pic")
    val picture: String,
    @JsonProperty("link")
    val link: String?
) : Parcelable
