package com.example.jetnytimesnews.compose.home

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.jetnytimesnews.R
import com.example.jetnytimesnews.compose.articles.NewsListScreen
import com.example.jetnytimesnews.data.network.NYTimesNewsCategory
import kotlinx.coroutines.launch

enum class HomeTabs(
    @StringRes val labelResId: Int
) {
    HOME(R.string.home),
    WORLD(R.string.world),
    SCIENCE(R.string.science),
    ARTS(R.string.arts)
}

@Composable
fun HomeScreen(
    tabs: Array<HomeTabs> = HomeTabs.entries.toTypedArray(),
    savedClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onNewsClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopAppBar(
                savedClick = savedClick
            )
        }
    ) { padding ->
        HomeScreen(
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            tabs = tabs,
            onNewsClick = onNewsClick,
            onShareClick = onShareClick
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    tabs: Array<HomeTabs>,
    onNewsClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
) {

    Column(modifier) {
        val pagerState = rememberPagerState(pageCount = { tabs.size })
        val currentPage by rememberUpdatedState(pagerState.currentPage)
        val coroutineScope = rememberCoroutineScope()
        val selectedTab = remember {
            mutableIntStateOf(0)
        }
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = dimensionResource(id = R.dimen.scrollable_tab_row_edge_start_padding)
        ) {
            tabs.forEachIndexed { index, homeTab ->
                val label = stringResource(id = homeTab.labelResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        selectedTab.intValue = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = label) },
                    unselectedContentColor = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { pageIndex ->
            if (pageIndex == currentPage) {
                NewsListScreen(
                    category = NYTimesNewsCategory.entries[pageIndex].category,
                    onNewsClick = onNewsClick,
                    onShareClick = onShareClick
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    savedClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.headlines),
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier
            )
        },
        actions = {
            IconButton(onClick = savedClick) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
            }
        }
    )

}
