package com.example.jetnytimesnews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetnytimesnews.data.network.NYTimesNewsResult
import com.example.jetnytimesnews.data.repository.NYTimesNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NYTimesNewsListViewModel @Inject constructor(
    private val repository: NYTimesNewsRepository,
) : ViewModel() {

    private val _news = MutableStateFlow<List<NYTimesNewsResult>?>(null)
    val news: LiveData<List<NYTimesNewsResult>>
        get() = _news.filterNotNull().asLiveData()

    private val _isLoading = MutableLiveData(true)
    val isLoading
        get() = _isLoading

    fun refreshData(category: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _news.value = emptyList()
            try {
                _news.value =
                    repository.getNYTimesNewsStream(category)
                        .first()
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun saveNews(nyTimesNewsResult: NYTimesNewsResult) {
        viewModelScope.launch {
            repository.saveNews(nyTimesNewsResult)
            val data = updateSavedForLaterState(nyTimesNewsResult, true)
            _news.update { data }
        }
    }

    fun deleteNews(nyTimesNewsResult: NYTimesNewsResult) {
        viewModelScope.launch {
            repository.deleteNews(nyTimesNewsResult.title)
            // TODO: Is there a better way?
            val data = updateSavedForLaterState(nyTimesNewsResult, false)
            _news.update { data }
        }
    }

    private fun updateSavedForLaterState(
        nyTimesNewsResult: NYTimesNewsResult,
        savedForLater: Boolean
    ): List<NYTimesNewsResult>? {
        return _news.value?.map {
            if (it.title == nyTimesNewsResult.title) {
                it.copy(savedForLater = savedForLater)
            } else {
                it
            }
        }
    }
}
