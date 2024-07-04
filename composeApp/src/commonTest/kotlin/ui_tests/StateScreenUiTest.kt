package ui_tests

import androidx.compose.material.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import backend_features.screen_model_state.ScreenModelState
import reusable_ui.StateScreen
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StateScreenUiTest {

    @Test
    fun test_StateScreen_loading_screen() = runComposeUiTest {
           setContent {
               StateScreen<Int>(
                   screenModelState = ScreenModelState.Loading,
                   onError = { errorMsg ->
                       Text(errorMsg)
                   },
                   content = { number ->
                       Text(number.toString())
                   }
               )
           }

        onNodeWithContentDescription(label = "Loading")
            .assertExists()
            .assertIsDisplayed()
    }


}