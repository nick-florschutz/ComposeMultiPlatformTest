package features.entries_list.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import features.entries_list.presentation.EntriesListScreenModel
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
                        .clickable {  }
                        .padding(16.sdp)
                )
            },
            contentPadding = PaddingValues(
                vertical = 8.sdp,
            ),
        )

    }
}

