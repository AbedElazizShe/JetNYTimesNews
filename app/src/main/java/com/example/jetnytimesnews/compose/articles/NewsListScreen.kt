package com.example.jetnytimesnews.compose.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnytimesnews.viewmodels.NYTimesNewsListViewModel

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NYTimesNewsListViewModel = hiltViewModel(),
    category: String,
    onNewsClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
) {

    val isLoading = viewModel.isLoading.observeAsState().value

    LaunchedEffect(category) {
        viewModel.refreshData(category)
    }

    val news by viewModel.news.observeAsState(initial = emptyList())

    if (isLoading == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        NewsListView(
            news = news,
            onNewsClick = onNewsClick,
            onShareClick = onShareClick
        ) { item, isSave ->
            if (isSave) {
                viewModel.saveNews(item)
            } else {
                viewModel.deleteNews(item)
            }
        }
    }
}
