import platform.UIKit.UIDevice

actual val platform: Platform =
    Platform(
        UIDevice.currentDevice.systemName(),
        UIDevice.currentDevice.systemVersion
    )