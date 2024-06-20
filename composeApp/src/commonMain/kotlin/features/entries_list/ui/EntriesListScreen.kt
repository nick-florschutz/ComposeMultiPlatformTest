package features.entries_list.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import data_sources.local.entities.Person
import features.entries_list.presentation.EntriesListScreenModel
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import reusable_ui.PullToRefreshLazyColumn

class EntriesListScreen: Screen {

    @Composable
    override fun Content() {

        val screenModel = koinScreenModel<EntriesListScreenModel>()

        val items = screenModel.items
        val textState by screenModel.textState

        val peopleList = screenModel.peopleList

        val isLoading by screenModel.isLoading

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.sdp)
            ) {
                Text(
                    text = textState,
                    fontSize = 24.ssp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.sdp))

                OutlinedTextField(
                    value = textState,
                    onValueChange = {
                        screenModel.setInputText(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.sdp)
                )

                Button(
                    onClick = {
                        screenModel.onSaveButtonClicked(
                            Person(
                                firstName = textState,
                                secondName = null,
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Save",
                        fontSize = 14.ssp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.sdp)
                    )
                }
            }

            PullToRefreshLazyColumn(
                items = peopleList,
                isRefreshing = isLoading,
                onRefresh = {
                    screenModel.onPullToRefreshTriggered()
                },
                itemContent = { item ->
                    Text(
                        text = "Item ${item.localId} ${item.firstName}",
                        fontSize = 14.ssp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.sdp, Color.Black)
                            .clickable {
                                println("Tag: $TAG \nItem ${item.localId} ${item.firstName} clicked")
                            }
                            .padding(16.sdp)
                    )
                },
                contentPadding = PaddingValues(
                    vertical = 8.sdp,
                ),
                modifier = Modifier.weight(1f)
            )
        }

    }

    companion object {
        private val TAG = this::class.simpleName
    }
}