package com.example.jetnytimesnews.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "news"
)
data class NewsEntity(
    @ColumnInfo("section") val section: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("abstract") val abstract: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("by_line") val byLine: String,
    @ColumnInfo("updated_date") val updatedDate: String,
    @ColumnInfo("display_date") val displayDate: String,
    @ColumnInfo("image_url") val imageUrl: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0
}
