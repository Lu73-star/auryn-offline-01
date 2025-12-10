# Auryn Offline - Personal AI Assistant

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Clean-orange.svg)

Auryn Offline is a personal AI assistant designed for **offline-first operation** with a focus on privacy, local data persistence, and clean architecture. Built primarily for Android, the project is structured to support future enhancements including voice interaction, tiered feature modules (Free/Pro/Ultra), and optional OpenAI API integration.

## 🎯 Core Features

- **🔒 Offline-First**: All AI processing done locally on device
- **💬 Conversational AI**: Chat interface with persistent conversation history
- **🗄️ Local Storage**: Room database for persistent memory
- **🏗️ Clean Architecture**: Separation of concerns with domain, data, and presentation layers
- **🎨 Modern UI**: Jetpack Compose with Material Design 3
- **📦 Modular Design**: Prepared for Free/Pro/Ultra tier features
- **🔮 Future-Ready**: Infrastructure for voice interaction and API integration

## 📋 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/auryn/offline/
│   │   │   ├── domain/          # Business logic layer
│   │   │   │   ├── model/       # Domain models
│   │   │   │   ├── repository/  # Repository interfaces
│   │   │   │   └── usecase/     # Use cases
│   │   │   ├── data/            # Data layer
│   │   │   │   ├── local/       # Room database
│   │   │   │   ├── remote/      # API interfaces (future)
│   │   │   │   └── repository/  # Repository implementations
│   │   │   ├── presentation/    # UI layer
│   │   │   │   ├── chat/        # Chat screen
│   │   │   │   ├── settings/    # Settings (future)
│   │   │   │   └── theme/       # Material Design theme
│   │   │   └── di/              # Dependency injection
│   │   └── res/                 # Android resources
│   └── test/                    # Unit tests
```

## 🛠️ Technology Stack

### Core
- **Language**: Kotlin 1.9.20
- **Build System**: Gradle with Kotlin DSL
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

### Android Components
- **UI Framework**: Jetpack Compose
- **Architecture Components**: ViewModel, LiveData, Navigation
- **Database**: Room 2.6.1
- **Preferences**: DataStore

### Architecture & DI
- **Dependency Injection**: Dagger Hilt 2.48
- **Coroutines**: Kotlin Coroutines 1.7.3
- **Architecture Pattern**: Clean Architecture (MVVM)

### Future Integration (Prepared)
- **Network**: Retrofit 2.9.0 + OkHttp 4.12.0
- **API**: OpenAI GPT integration structure
- **Voice**: TTS/STT infrastructure ready

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34
- Gradle 8.2+

### Building the Project

1. **Clone the repository**
   ```bash
   git clone https://github.com/Lu73-star/auryn-offline-01.git
   cd auryn-offline-01
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

### Building from Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 🏛️ Architecture

### Clean Architecture Layers

1. **Domain Layer** (Pure Kotlin)
   - Business logic and rules
   - Entity models
   - Repository interfaces
   - Use cases

2. **Data Layer**
   - Repository implementations
   - Data sources (Room, API)
   - Data models and mappers
   - Caching logic

3. **Presentation Layer**
   - ViewModels
   - Compose UI
   - UI state management
   - Navigation

### Key Design Decisions

- **Offline-First**: All features work without internet connection
- **Local ML Ready**: Architecture prepared for on-device AI models
- **Privacy-Focused**: No data leaves the device in offline mode
- **Scalable**: Modular structure for adding Pro/Ultra features
- **Testable**: Clean architecture enables easy unit testing

## 📱 Feature Modules (Planned)

### Free Tier (Current)
- ✅ Basic chat interface
- ✅ Local conversation storage
- ✅ Offline AI responses
- ✅ Conversation history

### Pro Tier (Future)
- 🔮 Voice input/output
- 🔮 Advanced AI models
- 🔮 Cloud sync (optional)
- 🔮 Custom personalities

### Ultra Tier (Future)
- 🔮 OpenAI API integration
- 🔮 Real-time information
- 🔮 Advanced analytics
- 🔮 Priority support

## 🧪 Testing

The project includes unit tests for core business logic:

```bash
# Run unit tests
./gradlew test

# Run with coverage
./gradlew testDebugUnitTest jacocoTestReport
```

Test structure:
- Domain layer use cases
- Repository implementations
- ViewModel logic

## 🔐 Privacy & Security

- **Local-First**: All processing happens on device
- **No Tracking**: No analytics or tracking in offline mode
- **Encrypted Storage**: Room database with encryption ready
- **Permission-Based**: Only requests necessary permissions
- **API Key Security**: Secure storage for future API integration

## 🗺️ Roadmap

### Phase 1: Foundation ✅
- [x] Project setup with clean architecture
- [x] Local database with Room
- [x] Basic chat interface
- [x] Offline AI responses

### Phase 2: Voice Integration 🚧
- [ ] Text-to-speech implementation
- [ ] Speech-to-text implementation
- [ ] Voice UI components
- [ ] Audio recording/playback

### Phase 3: Enhanced AI 🔮
- [ ] Integrate local ML models
- [ ] Improved response generation
- [ ] Context awareness
- [ ] Conversation summarization

### Phase 4: Online Features 🔮
- [ ] OpenAI API integration
- [ ] Online/offline mode switching
- [ ] Cloud backup (optional)
- [ ] Real-time data access

### Phase 5: Tier System 🔮
- [ ] Feature flag implementation
- [ ] In-app purchases
- [ ] Subscription management
- [ ] Pro/Ultra features

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add comments for complex logic
- Write unit tests for new features

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Android Jetpack team for excellent libraries
- Kotlin team for the amazing language
- Open source community for inspiration

## 📧 Contact

Project Link: [https://github.com/Lu73-star/auryn-offline-01](https://github.com/Lu73-star/auryn-offline-01)

---

**Built with ❤️ for privacy-conscious users**
