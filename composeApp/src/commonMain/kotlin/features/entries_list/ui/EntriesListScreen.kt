package features.entries_list.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import features.entries_list.presentation.EntriesListScreenModel
import io.github.aakira.napier.log
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import reusable_ui.PullToRefreshLazyColumn

class EntriesListScreen: Screen {

    @Composable
    override fun Content() {

        val screenModel = koinScreenModel<EntriesListScreenModel>()

        val items = screenModel.items

        val isLoading by screenModel.isLoading

        PullToRefreshLazyColumn(
            items = items,
            isRefreshing = isLoading,
            onRefresh = {
                screenModel.onPullToRefreshTriggered()
            },
            itemContent = { item ->
                Text(
                    text = "Item $item",
                    fontSize = 14.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.sdp, Color.Black)
                        .clickable {
                           log(tag = TAG, message = { "Item $item clicked" })
                        }
                        .padding(16.sdp)
                )
            },
            contentPadding = PaddingValues(
                vertical = 8.sdp,
            ),
        )

    }

    companion object {
        private val TAG = this::class.simpleName
    }
}