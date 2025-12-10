# Auryn Offline - Setup Guide

This guide will help you set up and run the Auryn Offline project on your development machine.

## Prerequisites

### Required Software

1. **Android Studio** (Hedgehog 2023.1.1 or later)
   - Download from: https://developer.android.com/studio
   - Includes Android SDK, emulator, and build tools

2. **Java Development Kit (JDK) 11 or later**
   - Android Studio includes OpenJDK
   - Or download from: https://adoptium.net/

3. **Git** (for cloning the repository)
   - Download from: https://git-scm.com/

### Optional but Recommended

- **Android Device** or **Android Emulator** (API 26+ / Android 8.0+)
- At least 8 GB RAM
- 4 GB free disk space

## Installation Steps

### 1. Clone the Repository

```bash
git clone https://github.com/Lu73-star/auryn-offline-01.git
cd auryn-offline-01
```

### 2. Open in Android Studio

1. Launch Android Studio
2. Select **"Open an Existing Project"**
3. Navigate to the cloned repository folder
4. Click **"OK"**

### 3. Sync Gradle

Android Studio will automatically start syncing Gradle. This process:
- Downloads all required dependencies
- Configures the build environment
- May take 5-10 minutes on first run

**If sync fails:**
- Check your internet connection
- Ensure Android SDK is properly installed
- Try **File > Invalidate Caches / Restart**

### 4. Configure Android SDK

1. Go to **Tools > SDK Manager**
2. Ensure the following are installed:
   - Android SDK Platform 34 (Android 14)
   - Android SDK Build-Tools 34.0.0 or later
   - Android Emulator (if using emulator)

### 5. Run the Application

#### Using an Emulator

1. Create an emulator:
   - Go to **Tools > Device Manager**
   - Click **"Create Device"**
   - Select a device definition (e.g., Pixel 5)
   - Choose **API 34** system image
   - Click **"Finish"**

2. Run the app:
   - Click the **green play button** in Android Studio
   - Or press **Shift + F10**
   - Select your emulator from the list

#### Using a Physical Device

1. Enable Developer Options on your Android device:
   - Go to **Settings > About Phone**
   - Tap **Build Number** 7 times
   - Return to Settings
   - Go to **System > Developer Options**
   - Enable **USB Debugging**

2. Connect your device via USB

3. Run the app:
   - Click the **green play button** in Android Studio
   - Select your physical device from the list

## Building from Command Line

### Debug Build

```bash
./gradlew assembleDebug
```

The APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

### Release Build

```bash
./gradlew assembleRelease
```

**Note:** Release builds require signing configuration. See [Signing Your App](https://developer.android.com/studio/publish/app-signing) for details.

## Running Tests

### Unit Tests

```bash
./gradlew test
```

### Unit Tests with Coverage

```bash
./gradlew testDebugUnitTest jacocoTestReport
```

Coverage report will be at:
```
app/build/reports/jacoco/jacocoTestReport/html/index.html
```

### Instrumented Tests (requires device/emulator)

```bash
./gradlew connectedAndroidTest
```

## Project Structure Overview

```
auryn-offline-01/
├── app/                          # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/auryn/offline/
│   │   │   │   ├── data/        # Data layer (repositories, database)
│   │   │   │   ├── domain/      # Business logic (models, use cases)
│   │   │   │   ├── presentation/# UI layer (screens, viewmodels)
│   │   │   │   └── di/          # Dependency injection
│   │   │   ├── res/             # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   └── test/                # Unit tests
│   └── build.gradle.kts         # App-level build configuration
├── gradle/                       # Gradle wrapper files
├── build.gradle.kts             # Project-level build configuration
├── settings.gradle.kts          # Project settings
└── README.md                    # Project documentation
```

## Common Issues and Solutions

### Issue: Gradle Sync Failed

**Solution:**
```bash
# Clean the project
./gradlew clean

# Clear Gradle cache
rm -rf ~/.gradle/caches/

# Re-sync in Android Studio
File > Sync Project with Gradle Files
```

### Issue: Kotlin Compilation Error

**Solution:**
- Ensure Kotlin plugin is up to date in Android Studio
- Check that `kotlinOptions.jvmTarget` matches `compileOptions` in `build.gradle.kts`

### Issue: Room Database Migration Error

**Solution:**
- Uninstall the app from device/emulator
- Or use fallback strategy in database configuration

### Issue: Hilt Dependency Injection Error

**Solution:**
- Ensure `@HiltAndroidApp` is on Application class
- Ensure `@AndroidEntryPoint` is on Activity
- Rebuild project: **Build > Rebuild Project**

### Issue: Compose Preview Not Showing

**Solution:**
- Update Android Studio to latest version
- Invalidate caches: **File > Invalidate Caches / Restart**
- Ensure `@Preview` annotation is present on composable

## Development Workflow

### 1. Making Changes

1. Create a new branch for your feature:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes in the appropriate layer:
   - **Domain Layer**: Business logic, models, use cases
   - **Data Layer**: Database, repositories, API
   - **Presentation Layer**: UI, ViewModels

3. Write tests for your changes

4. Run tests locally:
   ```bash
   ./gradlew test
   ```

### 2. Code Style

The project follows Kotlin coding conventions:
- Use meaningful variable and function names
- Follow single responsibility principle
- Add KDoc comments for public APIs
- Use data classes for models

### 3. Committing Changes

```bash
git add .
git commit -m "Brief description of changes"
git push origin feature/your-feature-name
```

## Performance Tips

### Speed Up Build Times

1. **Enable Gradle Daemon**
   Already enabled in `gradle.properties`

2. **Enable Build Cache**
   Already enabled in `gradle.properties`

3. **Increase Heap Size**
   Add to `gradle.properties`:
   ```properties
   org.gradle.jvmargs=-Xmx4096m
   ```

4. **Use Configuration Cache** (Gradle 8+)
   ```bash
   ./gradlew --configuration-cache
   ```

### Optimize Android Studio

1. **Increase IDE Memory**
   - **Help > Edit Custom VM Options**
   - Increase `-Xmx` value (e.g., `-Xmx4096m`)

2. **Disable Unused Plugins**
   - **Settings > Plugins**
   - Disable plugins you don't use

3. **Exclude Build Directories**
   - **Settings > Build > Compiler**
   - Exclude `build/` directories from indexing

## Debugging

### Enable Logging

The app uses Timber for logging. Logs are automatically enabled in debug builds.

View logs in Android Studio:
- **View > Tool Windows > Logcat**
- Filter by package: `com.auryn.offline`

### Using Android Profiler

1. **Run app in Debug mode**
2. **View > Tool Windows > Profiler**
3. Monitor:
   - CPU usage
   - Memory allocation
   - Network activity
   - Database queries

### Using Database Inspector

1. **Run app in Debug mode**
2. **View > Tool Windows > App Inspection**
3. Select **Database Inspector**
4. View and query Room database in real-time

## Next Steps

After successfully setting up the project:

1. **Explore the codebase** - Start with `MainActivity.kt` and `ChatScreen.kt`
2. **Run the app** - Try sending messages and see the offline AI responses
3. **Read the architecture docs** - See `ARCHITECTURE.md` for detailed information
4. **Contribute** - Check open issues and submit pull requests

## Getting Help

- **Documentation**: See `README.md` and `ARCHITECTURE.md`
- **Issues**: Submit bugs or feature requests on GitHub
- **Stack Overflow**: Tag questions with `android` and `kotlin`

## Resources

### Official Documentation

- [Android Developers](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Dagger Hilt](https://dagger.dev/hilt/)

### Learning Resources

- [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course)
- [Clean Architecture Guide](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)

---

**Happy Coding!** 🚀

If you encounter any issues not covered in this guide, please open an issue on GitHub.
