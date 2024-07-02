package backend_features.screen_model_state

sealed class ScreenModelStateError {
    data class NoItemsFound(val message: String) : ScreenModelStateError()
    data class Error(val message: String) : ScreenModelStateError()
}