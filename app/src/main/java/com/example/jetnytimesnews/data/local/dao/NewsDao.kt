package com.example.jetnytimesnews.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jetnytimesnews.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Query("SELECT title FROM news")
    fun getNewsTitles(): Flow<List<String>>

    @Insert
    suspend fun insertNews(newsEntity: NewsEntity): Long

    @Query("DELETE FROM news WHERE title = :title")
    suspend fun deleteNews(title: String)

    @Delete
    suspend fun deleteNews(newsEntity: NewsEntity)
}
