# Kotoba Dokodemo

A Japanese dictionary lookup app built with Kotlin Multiplatform, targeting Android and Desktop (JVM).

- Look up words from the Android share sheet or a desktop clipboard/OCR pipeline.
- Shared dictionary logic and database (SQLDelight) live in one codebase across both platforms.

## Prerequisites

- JDK 17 or newer (required by Gradle 9 / AGP 9). The Gradle wrapper handles the Gradle version itself.
- Android SDK with API level 36 installed, only if building the Android app (`compileSdk`/`targetSdk` 36, `minSdk` 24).

## Project structure

- [`shared/src/commonMain`](./shared/src/commonMain/kotlin) — code shared across all targets (dictionary lookup, database, view models, theming).
- [`shared/src/androidMain`](./shared/src/androidMain) — Android-specific implementations.
- [`shared/src/jvmMain`](./shared/src/jvmMain) — Desktop (JVM)-specific implementations, including the clipboard/OCR pipeline.
- [`androidApp`](./androidApp) — Android application module.
- [`desktopApp`](./desktopApp) — Desktop application module.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar, or these Gradle commands:

- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- Desktop tests: `./gradlew :shared:jvmTest`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
