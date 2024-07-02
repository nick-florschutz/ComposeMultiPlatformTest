package backend_features.screen_model_state

sealed class ScreenModelState {

    data object Loading : ScreenModelState()

    data class Success(val data: Any) : ScreenModelState()

    data class Error(val error: ScreenModelStateError) : ScreenModelState()

}