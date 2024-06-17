package features.entries_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EntriesListScreenModel : ScreenModel {

    private val _items = mutableStateListOf<Int>()
    val items: List<Int> = _items

    private val _textState = mutableStateOf("")
    val textState: State<String> = _textState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        screenModelScope.launch {
            delay(5000)
            println("Adding Items...")
            _items.addAll(
                (0..10)
            )
            println("Items Added. Total Items: ${_items.size}")
        }
    }

    fun setInputText(text: String) {
        _textState.value = text
    }

    fun onPullToRefreshTriggered() {
        screenModelScope.launch {
            _isLoading.value = true
            val job = launch {
                _items.addAll(
                    (items.last() + 1..items.last() + 50)
                )
                delay(3000)
            }
            job.join()
            println("Items Added. Total Items: ${_items.size}")
            _isLoading.value = false
        }
    }
}