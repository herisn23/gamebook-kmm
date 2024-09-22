This is a Kotlin Multiplatform project targeting Android, iOS.

* `/gameApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.
  - To run wasm
      - `./gradlew gameApp:wasmJsBrowserRun -t`
  - To build wasm
      - `./gradlew gameApp:wasmJsBrowserDistribution`
* `/editorApp` is wasmjs app to create stories
    * To start webpack server run `./gradlew editorApp:wasmJsBrowserRun -t`
        * For browser with chromium < 119 enable experimental flag feature
            * i.e. `brave://flags/#enable-experimental-webassembly-features`
            * Safari doesn't support Webassembly GC now, it is in development
    * To make productin build `./gradlew editorApp:wasmJsBrowserDistribution`
        * More [Here](https://kotlinlang.org/docs/wasm-get-started.html#publish-on-github-pages)
* `/gameApp`
* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

