package reusable_ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import backend_features.screen_model_state.ScreenModelState
import backend_features.screen_model_state.ScreenModelStateError

@Composable
fun <DataType> StateScreen(
    screenModelState: ScreenModelState,
    onError: @Composable (errorMsg: String) -> Unit,
    content: @Composable (data: DataType) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedContent(
            targetState = screenModelState,
            contentAlignment = Alignment.Center,
        ) { screenModelState ->

            @Suppress("UNCHECKED_CAST")
            when (screenModelState) {
                ScreenModelState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.semantics {
                            contentDescription = "Loading"
                        }
                    )
                }
                is ScreenModelState.Error -> {
                    when (screenModelState.error) {
                        is ScreenModelStateError.NoItemsFound -> {
                            NoItemsScreen(
                                modifier = Modifier.fillMaxSize(),
                                message = screenModelState.error.message
                            )
                        }
                        is ScreenModelStateError.Error -> {
                            onError(screenModelState.error.message)
                        }
                    }
                }
                is ScreenModelState.Success -> {
                    content(
                        screenModelState.data as DataType,
                    )
                }
            }

        }
    }
}