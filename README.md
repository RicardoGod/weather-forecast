# Weather Mate — Android Developer Quest (v1.0)

Welcome to **Weather Mate**, an educational Android development programme designed to teach junior developers progressively using Jetpack Compose, Material 3, and MVVM architecture.

## Multi-Project Architecture

This repository is configured as a **Gradle Multi-Project** workspace. Opening the root folder in Android Studio loads the **candidate starter kit (`:candidate-starter-app`)** side by side in the same window.

```text
weather-mate/
├── candidate/
│   ├── README.md
│   ├── missions/         # Missions 01 to 07
│   └── starter/          # Candidate starter project module (:candidate-starter-app)
├── mentor/               # Private mentor guides, rubrics & technical notes
├── evaluator/            # SSIM-based visual evaluator scripts and config
├── .github/workflows/    # CI pipeline for build and test checks
├── VALIDATION.md         # Build and test validation report
└── README.md
```

## Getting Started in Android Studio

1. Open the **root folder** (`weather-mate-android-quest-v1.0`) in Android Studio.
2. Android Studio will automatically recognize both modules (`:candidate-starter-app`).
3. Sync project with Gradle files.
4. Select either module from the run configuration dropdown to execute on an emulator or device.

## Building and Testing via Command Line

- Build all modules (Debug):
  ```bash
  ./gradlew assembleDebug
  ```
- Run unit tests:
  ```bash
  ./gradlew testDebugUnitTest
  ```
