package features.camera.data

import androidx.compose.runtime.saveable.listSaver
import kotlinx.serialization.Serializable

data class CameraPermissionState(
    val uiPermissionState: UiPermissionState,
    val isAlwaysDeniedDialogVisible: Boolean = false
) {

    companion object {
        val CameraPermissionStateSaver = listSaver<CameraPermissionState, Any>(
            save = { listOf(it.uiPermissionState, it.isAlwaysDeniedDialogVisible) },
            restore = {
                CameraPermissionState(
                    uiPermissionState = it[0] as UiPermissionState,
                    isAlwaysDeniedDialogVisible = it[1] as Boolean
                )
            }
        )
    }
}