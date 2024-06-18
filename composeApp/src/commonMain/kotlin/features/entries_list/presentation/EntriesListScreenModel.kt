package features.entries_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data_sources.preferences.DataStoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EntriesListScreenModel(
    private val dataStoreRepository: DataStoreRepository
) : ScreenModel {

    private val _items = mutableStateListOf<Int>().apply {
        addAll(0..50)
    }
    val items: List<Int> = _items

    private val _textState = mutableStateOf("")
    val textState: State<String> = _textState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        screenModelScope.launch {
            dataStoreRepository.readStringValue(savedTextDataStoreKey).collect {
                _textState.value = it
            }

        }
    }

    fun setInputText(text: String) {
//        _textState.value = text

        screenModelScope.launch {
            dataStoreRepository.saveStringValue(savedTextDataStoreKey, text)
        }
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

    companion object {
        private const val savedTextDataStoreKey = "saved_text"
    }
}