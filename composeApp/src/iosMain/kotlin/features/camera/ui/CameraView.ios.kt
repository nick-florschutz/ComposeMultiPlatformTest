package features.camera.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.position
import platform.CoreGraphics.CGRect
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraPreviewView(modifier: Modifier) {
    val device = remember {
        AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
            (device as AVCaptureDevice).position == AVCaptureDevicePositionBack
        }!! as AVCaptureDevice
    }

    val input = remember {
        AVCaptureDeviceInput.deviceInputWithDevice(device, null) as AVCaptureDeviceInput
    }

    val output = remember {
        AVCaptureStillImageOutput().apply {
            outputSettings = mapOf(AVVideoCodecKey to AVVideoCodecJPEG)
        }
    }

    val session = remember {
        AVCaptureSession().apply {
            sessionPreset = AVCaptureSessionPresetPhoto
            addInput(input)
            addOutput(output)
        }
    }

    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }

    UIKitView(
        modifier = modifier.fillMaxSize(),
        background = Color.Black,
        factory = {
            val container = UIView()
            container.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            session.startRunning()
            container
        },
        onResize = { container: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            container.layer.setFrame(rect)
            cameraPreviewLayer.setFrame(rect)
            CATransaction.commit()
        },
        onRelease = { container: UIView ->
            session.stopRunning()
            session.removeInput(input)
            session.removeOutput(output)
        }
    )
}