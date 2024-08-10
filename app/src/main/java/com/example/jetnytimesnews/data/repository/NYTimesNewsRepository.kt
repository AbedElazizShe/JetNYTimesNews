package com.example.jetnytimesnews.data.repository

import com.example.jetnytimesnews.api.NYTimesNewsService
import com.example.jetnytimesnews.data.local.dao.NewsDao
import com.example.jetnytimesnews.data.local.entity.NewsEntity
import com.example.jetnytimesnews.data.network.NYTimesNewsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NYTimesNewsRepository @Inject constructor(
    private val service: NYTimesNewsService,
    private val newsDao: NewsDao
) {
    fun getNYTimesNewsStream(category: String): Flow<List<NYTimesNewsResult>> = flow {
        newsDao.getNewsTitles().collect { titles ->
            val results = service.getNews(category).data
            results.forEach {
                if (titles.contains(it.title)) {
                    it.savedForLater = true
                }
            }
            val sorted = results.sortedByDescending { it.updatedDate }
            emit(sorted)
        }

    }

    fun getSavedNews(): Flow<List<NewsEntity>> = flow {
        newsDao.getNews().collect { news ->
            val sorted = news.sortedByDescending { it.updatedDate }
            emit(sorted)
        }

    }

    suspend fun saveNews(news: NYTimesNewsResult) {
        newsDao.insertNews(news.toEntity())
    }

    suspend fun deleteNews(title: String) {
        newsDao.deleteNews(title)
    }

    suspend fun deleteNews(newsEntity: NewsEntity) {
        newsDao.deleteNews(newsEntity)
    }
}

fun NYTimesNewsResult.toEntity(): NewsEntity {
    return NewsEntity(
        this.section,
        this.title,
        this.abstract,
        this.url,
        this.byLine,
        this.updatedDate,
        this.date,
        this.imageUrl,
    )
}
