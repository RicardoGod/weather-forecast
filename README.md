# Weather Mate — Android Developer Quest (v1.0)

Welcome to **Weather Mate**, an educational Android development programme designed to teach junior developers progressively using Jetpack Compose, Material 3, and MVVM architecture.

## Repository Structure

This repository contains the **candidate starter project**, designed for candidates to work on their missions step-by-step:

```text
weather-mate/
├── app/                  # Candidate starter app module (with TODOs)
├── candidate/            # Mission guides & instructions (Missions 01 to 07)
├── mentor/               # Private mentor guides, rubrics & reference solutions
├── evaluator/            # SSIM-based visual evaluator scripts and config
├── .github/workflows/    # CI pipeline for build and test checks
└── README.md
```

## Getting Started

1. Open the repository root folder in **Android Studio**.
2. Sync project with Gradle files.
3. Run the app on an emulator or physical device.

## Building and Testing via Command Line

- Build debug APK:
  ```bash
  ./gradlew assembleDebug
  ```
- Run unit tests:
  ```bash
  ./gradlew testDebugUnitTest
  ```
