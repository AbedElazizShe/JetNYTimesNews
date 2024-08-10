package com.example.jetnytimesnews.compose.articles

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetnytimesnews.data.local.entity.NewsEntity
import com.example.jetnytimesnews.data.network.NYTimesNewsResult
import com.example.jetnytimesnews.ui.theme.JetNYTimesNewsTheme

@Composable
fun NewsItemView(
    newsItem: NYTimesNewsResult,
    onClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onSaveClick: (NYTimesNewsResult, Boolean) -> Unit
) {
    ItemView(
        url = newsItem.url,
        imageUrl = newsItem.imageUrl,
        category = newsItem.category,
        title = newsItem.title,
        abstract = newsItem.abstract,
        date = newsItem.date,
        savedForLater = newsItem.savedForLater,
        onClick = onClick,
        onShareClick = onShareClick,
        onSaveClick = {
            onSaveClick(newsItem, it)
        }
    )
}

@Composable
fun NewsItemView(
    newsItem: NewsEntity,
    onClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onUnSaveClick: (NewsEntity) -> Unit
) {
    ItemView(
        url = newsItem.url,
        imageUrl = newsItem.imageUrl,
        category = newsItem.section,
        title = newsItem.title,
        abstract = newsItem.abstract,
        date = newsItem.displayDate,
        savedForLater = true,
        onClick = onClick,
        onShareClick = onShareClick,
        onSaveClick = {
            onUnSaveClick(newsItem)
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ItemView(
    url: String,
    imageUrl: String,
    category: String,
    title: String,
    abstract: String,
    date: String,
    savedForLater: Boolean,
    onClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onSaveClick: (Boolean) -> Unit
) {
    Card(
        onClick = { onClick(url) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .padding(bottom = 8.dp, top = 8.dp)
    ) {

        Column(Modifier.fillMaxWidth()) {
            GlideImage(
                model = imageUrl,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(185.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = category,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Text(
                text = abstract,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Row {
                    IconButton(onClick = {
                        if (savedForLater) onSaveClick(false) else onSaveClick(
                            true
                        )
                    }) {
                        Icon(
                            if (savedForLater) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        onShareClick(url)
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = null)
                    }
                }
            }
        }

    }
}

@Preview(wallpaper = Wallpapers.NONE,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ItemViewPreview() {
    JetNYTimesNewsTheme {
        ItemView(
            url = "",
            imageUrl = "",
            category = "Test",
            title = "Test",
            abstract = "Test",
            date = "Test",
            savedForLater = true,
            onClick = {},
            onShareClick = {}
        ) {
            
        }
    }
}
