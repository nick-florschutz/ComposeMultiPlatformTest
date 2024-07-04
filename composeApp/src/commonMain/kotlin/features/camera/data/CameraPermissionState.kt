package features.camera.data

import androidx.compose.runtime.saveable.listSaver

/**
 * Holds the state of the camera permission dialog.
 * @param uiPermissionState The state of the camera permission dialog.
 * @param isAlwaysDeniedDialogVisible Whether the always denied dialog is visible.
 */
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