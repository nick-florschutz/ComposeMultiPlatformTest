package reusable_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import network.chaintech.sdpcomposemultiplatform.sdp

/**
 * Generic lazy column that supports pull-to-refresh.
 *
 * @param modifier
 * @param onRefresh lambda function called when user pulls down to refresh.
 * @param items list of items to be displayed in the lazy column.
 * @param itemContent content to be shown in the lazy column.
 * @param isRefreshing boolean value indicating whether the lazy column is currently refreshing.
 * @param contentPadding padding values for the lazy column.
 * @param lazyColumnHorizontalAlignment horizontal alignment for the lazy column.
 * @param lazyColumnVerticalArrangement vertical arrangement for the lazy column.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <ItemType> PullToRefreshLazyColumn(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    items: List<ItemType>,
    itemContent: @Composable (ItemType) -> Unit,
    isRefreshing: Boolean,
    contentPadding: PaddingValues = PaddingValues(0.sdp),
    lazyColumnHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    lazyColumnVerticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.sdp),
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier.pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            horizontalAlignment = lazyColumnHorizontalAlignment,
            verticalArrangement = lazyColumnVerticalArrangement,
            contentPadding = contentPadding,
        ) {
            items(items) {
                itemContent(it)
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}