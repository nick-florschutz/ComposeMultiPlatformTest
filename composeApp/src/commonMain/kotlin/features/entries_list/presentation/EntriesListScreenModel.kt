package features.entries_list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data_sources.local.PeopleDao
import data_sources.local.entities.Person
import data_sources.preferences.DataStoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EntriesListScreenModel(
    private val dataStoreRepository: DataStoreRepository,
    private val peopleDao: PeopleDao,
) : ScreenModel {

    private val _items = mutableStateListOf<Int>().apply {
        addAll(0..50)
    }
    val items: List<Int> = _items

    private val _peopleList = mutableStateListOf<Person>()
    val peopleList: List<Person> = _peopleList

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

        loadPeople()
    }

    fun setInputText(text: String) {
//        _textState.value = text

        screenModelScope.launch {
            dataStoreRepository.saveStringValue(savedTextDataStoreKey, text)
        }
    }

    private fun loadPeople() {
        screenModelScope.launch {
            peopleDao.getAllPeople().collect {
                _peopleList.clear()
                _peopleList.addAll(it)
            }
        }
    }

    fun onSaveButtonClicked(person: Person) {
        screenModelScope.launch {
            peopleDao.upsert(person)
        }
    }

    fun onPullToRefreshTriggered() {
        screenModelScope.launch {
            _isLoading.value = true
            delay(3000)

            println("Loading Complete....")

            _isLoading.value = false
        }
    }

    companion object {
        private const val savedTextDataStoreKey = "saved_text"
    }
}