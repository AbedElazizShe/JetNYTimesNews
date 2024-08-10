package com.example.jetnytimesnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnytimesnews.data.local.entity.NewsEntity
import com.example.jetnytimesnews.data.repository.NYTimesNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedForLaterViewModel @Inject constructor(
    private val repository: NYTimesNewsRepository
): ViewModel() {
    val newsList: Flow<List<NewsEntity>> =
        repository.getSavedNews()

    fun deleteNews(newsEntity: NewsEntity) {
        viewModelScope.launch {
            repository.deleteNews(newsEntity)
        }
    }
}
