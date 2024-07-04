package features.camera.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FlipCameraIos
import androidx.compose.material.icons.filled.Photo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import features.camera.data.CameraPermissionState
import features.camera.data.UiPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
expect fun CameraPreviewView(modifier: Modifier = Modifier)

@Composable
fun CameraView(
    modifier: Modifier = Modifier.fillMaxSize(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onDismiss: () -> Unit,
) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val permissionsController: PermissionsController =
        remember(factory) { factory.createPermissionsController() }

    BindEffect(permissionsController)

    var state by rememberSaveable(stateSaver = CameraPermissionState.CameraPermissionStateSaver) {
        mutableStateOf(
            CameraPermissionState(
                uiPermissionState = UiPermissionState.INITIAL,
                isAlwaysDeniedDialogVisible = false,
            )
        )
    }
    var hasPermissionAlready by rememberSaveable { mutableStateOf(false) }
    /*
    * hasPermissionAlready can be take time to return the value as it is suspend
    * so we should show place holder like CircularProgressIndicator
    * */
    var isPermissionChecked by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (permissionsController.isPermissionGranted(Permission.CAMERA)) {
            hasPermissionAlready = permissionsController.isPermissionGranted(Permission.CAMERA)
            isPermissionChecked = true
            return@LaunchedEffect
        }

        try {
            permissionsController.providePermission(Permission.CAMERA)
            state = state.copy(uiPermissionState = UiPermissionState.GRANTED)
        } catch (deniedAlways: DeniedAlwaysException) {
            state = state.copy(
                uiPermissionState = UiPermissionState.ALWAYS_DENIED,
                isAlwaysDeniedDialogVisible = true,
            )
        } catch (denied: DeniedException) {
            state = state.copy(uiPermissionState = UiPermissionState.DENIED_ONCE)
        }

        hasPermissionAlready = permissionsController.isPermissionGranted(Permission.CAMERA)
        isPermissionChecked = true
    }

    AnimatedContent(isPermissionChecked) { checked ->
        if (checked) {
            AnimatedContent(hasPermissionAlready) { hasPermission ->
                if (hasPermission) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color.Black)
                                .weight(0.08F)
                                .fillMaxWidth()
                                .padding(8.sdp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.sdp)
                                    .clickable {
                                        onDismiss()
                                    }
                            )
                        }

                        CameraPreviewView(modifier = Modifier.weight(0.8F))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(Color.Black)
                                .weight(0.12f)
                                .fillMaxWidth()
                                .padding(16.sdp)
                        ) {
                            Icon(
                                Icons.Default.Photo,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.sdp)
                            )

                            Icon(
                                Icons.Default.Circle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.sdp)
                            )

                            Icon(
                                Icons.Default.FlipCameraIos,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.sdp)
                            )
                        }
                    }
                } else {
                    CameraPermission(
                        state = state,
                        onRequestPermission = {
                            try {
                                coroutineScope.launch {
                                    permissionsController.providePermission(Permission.CAMERA)
                                }
                                state = state.copy(uiPermissionState = UiPermissionState.GRANTED)
                            } catch (deniedAlways: DeniedAlwaysException) {
                                state = state.copy(
                                    uiPermissionState = UiPermissionState.ALWAYS_DENIED,
                                    isAlwaysDeniedDialogVisible = true,
                                )
                            } catch (denied: DeniedException) {
                                state =
                                    state.copy(uiPermissionState = UiPermissionState.DENIED_ONCE)
                            }
                        },
                        openSettings = { permissionsController.openAppSettings() },
                        onDismiss = {}
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
                Text("Loading...")
            }
        }
    }
}

@Composable
fun CameraPermission(
    modifier: Modifier = Modifier,
    state: CameraPermissionState,
    onRequestPermission: () -> Unit,
    openSettings: () -> Unit,
    onDismiss: () -> Unit,
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "We need camera permission for this app to function",
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
            Spacer(Modifier.size(8.dp))
            Button(
                onClick = { onRequestPermission() }
            ) {
                Text("Open camera")
            }

        }
    }

    when {
        state.isAlwaysDeniedDialogVisible -> AlwaysDeniedDialog(
            onOpenSettings = openSettings,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun AlwaysDeniedDialog(
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        backgroundColor = Color.White,
        confirmButton = {
            TextButton(onClick = {
                onOpenSettings()
                onDismiss()

            }) {
                Text("Open app settings")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text(text = "Camera permission required") },
        text = { Text("We need camera permission in order to use the camera") }
    )

}

