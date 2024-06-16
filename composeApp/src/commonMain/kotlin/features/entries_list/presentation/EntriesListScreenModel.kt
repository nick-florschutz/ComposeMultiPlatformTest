package features.entries_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EntriesListScreenModel : ScreenModel {

    private val _items = mutableStateOf(0)
    val items: State<Int> = _items

    private val _textState = mutableStateOf("")
    val textState: State<String> = _textState

    init {
        screenModelScope.launch {
            delay(5000)
            println("Adding Items...")
            _items.value = 100
            println("Items Added. Totel Items: ${_items.value}")
        }
    }

    fun setInputText(text: String) {
        _textState.value = text
    }
}