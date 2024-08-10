package com.example.jetnytimesnews.data.network

import android.text.format.DateUtils
import com.example.jetnytimesnews.utils.formatDate
import com.google.gson.annotations.SerializedName
import java.util.Calendar
import java.util.Date

data class NYTimesNewsResponse(
    @field:SerializedName("results") val results: List<NYTimesNewsResult>,
    @field:SerializedName("num_results") val numResults: Int
) {
    val data: List<NYTimesNewsResult>
        get() {
            // exclude results that have empty data which the api returns sometimes.
            return results.filter { it.multimedia != null && it.url != "null" }
        }
}

data class NYTimesNewsResult(
    @field:SerializedName("section") val section: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("abstract") val abstract: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("byline") val byLine: String,
    @field:SerializedName("updated_date") val updatedDate: String,
    @field:SerializedName("multimedia") val multimedia: List<Multimedia>?,
    var savedForLater: Boolean = false
) {
    val date: String
        get() {
            val formattedDate: Date = formatDate(updatedDate)

            return DateUtils.getRelativeTimeSpanString(
                formattedDate.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            )
                .toString()
        }

    val imageUrl: String
        get() {
            return multimedia?.first { it.format == "threeByTwoSmallAt2X" }?.url ?: ""
        }
    val category: String
        get() {
            return  section.uppercase()
        }
}

data class Multimedia(
    @field:SerializedName("url") val url: String,
    @field:SerializedName("format") val format: String
)

enum class NYTimesNewsCategory(
    val category: String
) {
    HOME("home"),
    WORLD("world"),
    SCIENCE("science"),
    ARTS("arts"),
}
