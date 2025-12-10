# Auryn Offline - Architecture Documentation

## Overview

Auryn Offline is built using **Clean Architecture** principles to ensure maintainability, testability, and scalability. The application is structured into three main layers, each with specific responsibilities and dependencies.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                   Presentation Layer                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Activity   │  │  ViewModel   │  │  Compose UI  │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└────────────────────────┬────────────────────────────────┘
                         │
                         ↓
┌─────────────────────────────────────────────────────────┐
│                     Domain Layer                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Use Cases  │  │    Models    │  │ Repositories │  │
│  │              │  │              │  │  (Interface) │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└────────────────────────┬────────────────────────────────┘
                         │
                         ↓
┌─────────────────────────────────────────────────────────┐
│                      Data Layer                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ Repositories │  │     Room     │  │   Retrofit   │  │
│  │    (Impl)    │  │   Database   │  │     API      │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
```

## Layer Responsibilities

### 1. Presentation Layer

**Location**: `presentation/`

**Responsibilities**:
- Display data to users
- Handle user interactions
- Manage UI state
- Navigate between screens

**Components**:
- **MainActivity**: Entry point of the application
- **ChatScreen**: Main chat interface with Compose
- **ChatViewModel**: Manages chat UI state and user interactions
- **Theme**: Material Design 3 theme configuration

**Key Technologies**:
- Jetpack Compose for declarative UI
- Material Design 3 components
- ViewModel for state management
- StateFlow for reactive data

**Dependencies**: Domain Layer (Use Cases, Models)

### 2. Domain Layer

**Location**: `domain/`

**Responsibilities**:
- Business logic and rules
- Define core entities
- Use case orchestration
- Define data contracts

**Components**:

#### Models (`domain/model/`)
- `Message`: Represents a chat message
- `Conversation`: Represents a conversation thread
- `UserPreferences`: User settings and preferences
- `TierLevel`: Enum for Free/Pro/Ultra tiers
- `ThemeMode`: Enum for theme settings

#### Repositories (`domain/repository/`)
- `MessageRepository`: Contract for message operations
- `ConversationRepository`: Contract for conversation operations
- `PreferencesRepository`: Contract for preferences management

#### Use Cases (`domain/usecase/`)
- `SendMessageUseCase`: Handles sending messages and generating AI responses
- `GetConversationMessagesUseCase`: Retrieves messages for a conversation

**Key Principles**:
- Pure Kotlin (no Android dependencies)
- Framework-independent
- Easily testable
- Single Responsibility Principle

**Dependencies**: None (innermost layer)

### 3. Data Layer

**Location**: `data/`

**Responsibilities**:
- Implement repository interfaces
- Manage data sources
- Handle data caching
- Map between data and domain models

**Components**:

#### Local Data Source (`data/local/`)
- `AurynDatabase`: Room database definition
- `MessageDao`: Data access for messages
- `ConversationDao`: Data access for conversations
- `MessageEntity`: Room entity for messages
- `ConversationEntity`: Room entity for conversations

#### Remote Data Source (`data/remote/`)
- `OpenAIApi`: Retrofit interface for OpenAI API (future use)
- `ChatCompletionRequest`: DTO for API requests
- `ChatCompletionResponse`: DTO for API responses

#### Repository Implementations (`data/repository/`)
- `MessageRepositoryImpl`: Implements MessageRepository using Room
- `ConversationRepositoryImpl`: Implements ConversationRepository using Room
- `PreferencesRepositoryImpl`: Implements PreferencesRepository using DataStore

**Key Technologies**:
- Room for local database
- Retrofit for future API calls
- DataStore for preferences
- Kotlin Coroutines for async operations

**Dependencies**: Domain Layer (Repository Interfaces, Models)

## Dependency Injection

**Framework**: Dagger Hilt

**Module**: `di/AppModule.kt`

**Provides**:
- Database instances
- DAO instances
- Repository implementations
- Network clients (OkHttp, Retrofit)
- API interfaces
- DataStore instance

**Scope**: All dependencies are Singleton-scoped for application lifecycle

## Data Flow

### User Sends Message

```
User Input
    ↓
ChatScreen (Compose)
    ↓
ChatViewModel.sendMessage()
    ↓
SendMessageUseCase.invoke()
    ↓
MessageRepository.insertMessage()
    ↓
MessageDao (Room)
    ↓
Database
    ↓
Flow<List<Message>> emits update
    ↓
ChatViewModel updates UI state
    ↓
ChatScreen re-composes with new messages
```

### Load Conversation History

```
App Start
    ↓
ChatViewModel.init()
    ↓
GetConversationMessagesUseCase.invoke()
    ↓
MessageRepository.getMessagesForConversation()
    ↓
MessageDao.getMessagesForConversation() returns Flow
    ↓
Continuous observation of database changes
    ↓
ChatViewModel collects Flow
    ↓
UI updates automatically on data changes
```

## State Management

### UI State Pattern

Each screen has a `UiState` data class:

```kotlin
data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val voiceEnabled: Boolean = false,
    val offlineMode: Boolean = true
)
```

**Benefits**:
- Single source of truth
- Immutable state
- Easy to test
- Predictable updates

### ViewModel Pattern

ViewModels manage UI state and handle business logic:

```kotlin
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    // ... other dependencies
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    fun sendMessage(content: String) {
        viewModelScope.launch {
            // Update UI state and execute use case
        }
    }
}
```

## Database Schema

### Messages Table

```sql
CREATE TABLE messages (
    id TEXT PRIMARY KEY NOT NULL,
    conversationId TEXT NOT NULL,
    content TEXT NOT NULL,
    isFromUser INTEGER NOT NULL,
    timestamp INTEGER NOT NULL,
    voiceEnabled INTEGER NOT NULL
);
```

### Conversations Table

```sql
CREATE TABLE conversations (
    id TEXT PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    createdAt INTEGER NOT NULL,
    updatedAt INTEGER NOT NULL,
    messageCount INTEGER NOT NULL
);
```

## Future Architecture Enhancements

### 1. Voice Module

**Structure**:
```
voice/
├── tts/              # Text-to-speech
├── stt/              # Speech-to-text
└── recorder/         # Audio recording
```

**Integration**: New use cases in domain layer for voice operations

### 2. AI Module

**Structure**:
```
ai/
├── models/           # Local ML models
├── inference/        # Model inference logic
└── training/         # Model fine-tuning (future)
```

**Integration**: Replaces simple response generation in SendMessageUseCase

### 3. Tier Management

**Structure**:
```
tier/
├── FeatureFlag       # Feature availability
├── LicenseManager   # License verification
└── UpgradeManager   # In-app purchase handling
```

**Integration**: Inject into use cases to check feature availability

### 4. API Integration

**Current State**: Structure prepared but not active

**Future**: 
- Toggle between offline and online mode
- Fallback to local processing if API unavailable
- Cache API responses for offline access

## Testing Strategy

### Unit Tests

**Focus**: Domain layer (use cases, business logic)

**Tools**: JUnit, MockK, Coroutines Test

**Example**:
```kotlin
@Test
fun `sendMessage should insert user message and AI response`() = runTest {
    // Test use case logic in isolation
}
```

### Integration Tests

**Focus**: Data layer (repository implementations, database)

**Tools**: Room Testing, AndroidX Test

### UI Tests

**Focus**: Presentation layer (user interactions, navigation)

**Tools**: Compose Testing, Espresso

## Best Practices

### Code Organization

- One class per file
- Package by feature (when features grow)
- Keep files under 300 lines
- Use meaningful names

### Kotlin Practices

- Use data classes for models
- Prefer immutability
- Use sealed classes for states
- Leverage coroutines for async operations

### Clean Architecture Rules

1. **Dependency Rule**: Dependencies point inward
2. **Interface Segregation**: Small, focused interfaces
3. **Dependency Inversion**: Depend on abstractions
4. **Single Responsibility**: One reason to change

### Performance Considerations

- Use Flow for reactive data
- Leverage Room's async operations
- Implement pagination for large lists (future)
- Use lazy loading where appropriate

## Security Considerations

### Data Protection

- Room database encryption ready
- Secure storage for API keys (KeyStore)
- No sensitive data in logs (Timber configuration)
- HTTPS only for API calls

### Permissions

- Request permissions at runtime
- Minimal permission set
- Clear explanations to users

## Scalability

The architecture is designed to scale in multiple dimensions:

1. **Feature Scalability**: Easy to add new features without affecting existing code
2. **Team Scalability**: Different teams can work on different layers
3. **Code Scalability**: Modular structure prevents code complexity
4. **Performance Scalability**: Optimized data flow and caching strategies

## Conclusion

Auryn Offline's architecture prioritizes:
- **Separation of Concerns**: Clear layer boundaries
- **Testability**: Easy to write and maintain tests
- **Flexibility**: Easy to swap implementations
- **Maintainability**: Clear structure and patterns
- **Scalability**: Ready for future enhancements

This architecture provides a solid foundation for building a robust, privacy-focused, offline-first AI assistant.
