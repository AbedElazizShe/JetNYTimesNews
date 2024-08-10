package com.example.jetnytimesnews.compose.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.jetnytimesnews.data.network.NYTimesNewsResult
import com.example.jetnytimesnews.ui.theme.JetNYTimesNewsTheme

@Composable
fun NewsListView(
    news: List<NYTimesNewsResult>,
    onNewsClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onSaveClick: (NYTimesNewsResult, Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
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
                    onSaveClick = onSaveClick
                )
            }

        }
    }
}

@Preview
@Composable
fun NewsListViewPreview(
    @PreviewParameter(NewsPreviewParamProvider::class) news: List<NYTimesNewsResult>
) {
    JetNYTimesNewsTheme {
        NewsListView(news = news, onNewsClick = {}, onShareClick = {}, onSaveClick = { _, _ -> })
    }
}

private class NewsPreviewParamProvider : PreviewParameterProvider<List<NYTimesNewsResult>> {
    override val values: Sequence<List<NYTimesNewsResult>> =
        sequenceOf(
            emptyList(),
            listOf(
                NYTimesNewsResult(
                    "",
                    "Move Over, La Guardia and Newark: 18 Artists to Star at New J.F.K. Terminal",
                    "Terminal 6 at Kennedy International Airport will feature work by Charles Gaines, Barbara Kruger and more. Developers of new terminals must invest in public art.",
                    "https://www.nytimes.com/2024/07/16/arts/design/artists-commissioned-jfk-airport.html",
                    "By Hilarie M. Sheets",
                    "2024-07-16T08:06:06-04:00",
                    multimedia = emptyList()
                )
            )
        )

}
