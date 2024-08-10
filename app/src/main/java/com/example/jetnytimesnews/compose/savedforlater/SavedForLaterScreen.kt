package com.example.jetnytimesnews.compose.savedforlater

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnytimesnews.R
import com.example.jetnytimesnews.compose.articles.NewsItemView
import com.example.jetnytimesnews.data.local.entity.NewsEntity
import com.example.jetnytimesnews.ui.theme.JetNYTimesNewsTheme
import com.example.jetnytimesnews.viewmodels.SavedForLaterViewModel

@Composable
fun SavedForLaterScreen(
    viewModel: SavedForLaterViewModel = hiltViewModel(),
    onUpClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onNewsClick: (String) -> Unit,
) {

    val news by viewModel.newsList.collectAsState(initial = emptyList())
    SavedNewsList(
        news,
        onUpClick = onUpClick,
        onNewsClick = onNewsClick,
        onShareClick = onShareClick,
        onUnSaveClick = {
            viewModel.deleteNews(it)
        }
    )
}

@Composable
private fun SavedNewsList(
    news: List<NewsEntity>,
    onUpClick: () -> Unit,
    onNewsClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onUnSaveClick: (NewsEntity) -> Unit
) {

    Scaffold(
        topBar = {
            SavedForLaterTopAppbar(
                onUpClick = onUpClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = padding.calculateTopPadding(), bottom = 24.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(
                    items = news,
                    key = { it.title }
                ) {
                    NewsItemView(
                        newsItem = it,
                        onClick = onNewsClick,
                        onShareClick = onShareClick,
                        onUnSaveClick = onUnSaveClick
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SavedForLaterTopAppbar(
    modifier: Modifier = Modifier,
    onUpClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.saved_for_later))
        },
        modifier = modifier
            .statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Preview
@Composable
private fun SavedNewsListPreview(
    @PreviewParameter(NewsEntityPreviewParamProvider::class) news: List<NewsEntity>
) {
    JetNYTimesNewsTheme {
        SavedNewsList(
            news = news,
            onUpClick = {},
            onNewsClick = {},
            onShareClick = {},
            onUnSaveClick = {}
        )
    }
}
private class NewsEntityPreviewParamProvider : PreviewParameterProvider<List<NewsEntity>> {
    override val values: Sequence<List<NewsEntity>> =
        sequenceOf(
            emptyList(),
            listOf(
                NewsEntity(
                    "US",
                    "Move Over, La Guardia and Newark: 18 Artists to Star at New J.F.K. Terminal",
                    "Terminal 6 at Kennedy International Airport will feature work by Charles Gaines, Barbara Kruger and more. Developers of new terminals must invest in public art.",
                    "https://www.nytimes.com/2024/07/16/arts/design/artists-commissioned-jfk-airport.html",
                    "By Hilarie M. Sheets",
                    "2024-07-16T08:06:06-04:00",
                    "1 hour ago",
                    "emptyList()"
                )
            )
        )

}
