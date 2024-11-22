package features.favorites_list.presentation

import androidx.compose.runtime.mutableStateListOf
import backend_features.screen_model_state.ScreenModelState
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class FavoritesListScreenScreenModel: StateScreenModel<ScreenModelState>(ScreenModelState.Loading) {

    val tilesList = mutableStateListOf<String>()

    init {
        screenModelScope.launch {
            populateTilesList()
            mutableState.emit(ScreenModelState.Success(Unit))
        }
    }

    private fun populateTilesList() {
        (1..100).forEach {
            tilesList.add("Tile $it")
        }
    }

}