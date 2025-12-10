# Contributing to Auryn Offline

Thank you for your interest in contributing to Auryn Offline! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

By participating in this project, you agree to maintain a respectful and collaborative environment. Be kind, constructive, and professional in all interactions.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:

1. **Clear title** describing the issue
2. **Steps to reproduce** the bug
3. **Expected behavior** vs actual behavior
4. **Environment details** (Android version, device model, app version)
5. **Screenshots or logs** if applicable

**Example:**
```
Title: App crashes when sending message with empty text

Steps to Reproduce:
1. Open the app
2. Leave message input empty
3. Click send button
4. App crashes

Expected: Message should not be sent, or show validation error
Actual: App crashes with NullPointerException

Environment:
- Android 14 (API 34)
- Pixel 5 Emulator
- App version 1.0.0
```

### Suggesting Features

Feature requests are welcome! Please create an issue with:

1. **Feature description** - What feature do you want?
2. **Use case** - Why is this feature needed?
3. **Proposed solution** - How should it work?
4. **Alternatives considered** - Other approaches you thought about

### Pull Requests

#### Before You Start

1. **Check existing issues** - Your feature might already be in progress
2. **Discuss major changes** - Create an issue first for significant changes
3. **Follow the architecture** - Maintain clean architecture principles

#### Development Process

1. **Fork the repository**
   ```bash
   # Fork on GitHub, then clone your fork
   git clone https://github.com/YOUR_USERNAME/auryn-offline-01.git
   cd auryn-offline-01
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
   
   Branch naming conventions:
   - `feature/` - New features
   - `fix/` - Bug fixes
   - `docs/` - Documentation changes
   - `refactor/` - Code refactoring
   - `test/` - Adding tests

3. **Make your changes**
   - Follow the coding standards (see below)
   - Write clear, concise commit messages
   - Add tests for new features
   - Update documentation if needed

4. **Test your changes**
   ```bash
   # Run unit tests
   ./gradlew test
   
   # Run on device/emulator
   ./gradlew installDebug
   ```

5. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add feature: brief description"
   ```
   
   Commit message format:
   ```
   Add feature: Brief description
   
   More detailed explanation of what changed and why.
   Fixes #123
   ```

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Go to the original repository on GitHub
   - Click "New Pull Request"
   - Select your fork and branch
   - Fill in the PR template

#### Pull Request Guidelines

**PR Title Format:**
- `[Feature] Add voice input support`
- `[Fix] Resolve crash on message send`
- `[Docs] Update setup instructions`
- `[Refactor] Improve message repository`

**PR Description Should Include:**
- Summary of changes
- Related issue numbers (Fixes #123)
- Testing done
- Screenshots (for UI changes)
- Breaking changes (if any)

**Example PR Description:**
```markdown
## Summary
Adds voice input support for sending messages

## Changes
- Added microphone permission handling
- Implemented speech-to-text functionality
- Updated UI with voice input button
- Added voice recording indicator

## Testing
- Tested on Pixel 5 (Android 14)
- Tested on Samsung Galaxy S21 (Android 13)
- Added unit tests for voice input logic
- Manual testing with various voice inputs

## Screenshots
[Include screenshots here]

Fixes #42
```

## Coding Standards

### Kotlin Style Guide

Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)

**Key Points:**
- Use 4 spaces for indentation (no tabs)
- Max line length: 120 characters
- Use camelCase for variables and functions
- Use PascalCase for classes
- Use UPPER_SNAKE_CASE for constants

**Example:**
```kotlin
class MessageRepository(
    private val messageDao: MessageDao
) {
    suspend fun insertMessage(message: Message) {
        messageDao.insert(message.toEntity())
    }
    
    companion object {
        const val MAX_MESSAGE_LENGTH = 1000
    }
}
```

### Architecture Guidelines

#### Clean Architecture Layers

1. **Domain Layer** (`domain/`)
   - Pure Kotlin (no Android dependencies)
   - Contains business logic
   - Models, use cases, repository interfaces

   ```kotlin
   // ✅ Good - Pure Kotlin, no Android imports
   data class Message(
       val id: String,
       val content: String,
       val timestamp: Long
   )
   
   // ❌ Bad - Android dependency in domain layer
   import android.os.Parcelable
   data class Message(...) : Parcelable
   ```

2. **Data Layer** (`data/`)
   - Repository implementations
   - Data sources (Room, Retrofit)
   - Data mapping

   ```kotlin
   // ✅ Good - Implements domain interface
   class MessageRepositoryImpl(
       private val messageDao: MessageDao
   ) : MessageRepository {
       override suspend fun getMessage(id: String): Message? {
           return messageDao.getMessage(id)?.toDomainModel()
       }
   }
   ```

3. **Presentation Layer** (`presentation/`)
   - ViewModels
   - Compose UI
   - UI state management

   ```kotlin
   // ✅ Good - ViewModel with use case injection
   @HiltViewModel
   class ChatViewModel @Inject constructor(
       private val sendMessageUseCase: SendMessageUseCase
   ) : ViewModel() {
       // ...
   }
   ```

#### Dependency Rules

- **Domain** depends on nothing
- **Data** depends on domain
- **Presentation** depends on domain (not data)

```
Presentation  →  Domain  ←  Data
    ↓                          ↓
   UI                      Database/API
```

### Naming Conventions

#### Files and Classes

- **ViewModels**: `ChatViewModel`, `SettingsViewModel`
- **Use Cases**: `SendMessageUseCase`, `GetMessagesUseCase`
- **Repositories**: `MessageRepository`, `ConversationRepository`
- **DAOs**: `MessageDao`, `ConversationDao`
- **Entities**: `MessageEntity`, `ConversationEntity`
- **Screens**: `ChatScreen`, `SettingsScreen`

#### Variables and Functions

```kotlin
// ✅ Good
val userId: String
val isLoading: Boolean
fun sendMessage(content: String)
fun onButtonClick()

// ❌ Bad
val user_id: String  // Don't use snake_case
val loading: Boolean  // Not descriptive enough
fun send()  // Too vague
```

### Comments and Documentation

#### KDoc for Public APIs

```kotlin
/**
 * Sends a message and generates an AI response.
 *
 * @param conversationId The ID of the conversation
 * @param content The message content
 * @param voiceEnabled Whether voice output is enabled
 * @return Result containing the AI response message or an error
 */
suspend fun sendMessage(
    conversationId: String,
    content: String,
    voiceEnabled: Boolean
): Result<Message>
```

#### Inline Comments

Use sparingly, only when code isn't self-explanatory:

```kotlin
// ✅ Good - Explains why, not what
// Delay is needed to prevent rate limiting
delay(1000)

// ❌ Bad - States the obvious
// Set loading to true
isLoading = true
```

### Testing Guidelines

#### Unit Tests

- Test business logic in domain layer
- Use MockK for mocking
- Follow AAA pattern (Arrange, Act, Assert)

```kotlin
@Test
fun `sendMessage should insert user message and AI response`() = runTest {
    // Arrange
    val conversationId = "test-id"
    val userInput = "Hello"
    
    // Act
    val result = sendMessageUseCase(conversationId, userInput, false)
    
    // Assert
    assertTrue(result.isSuccess)
    verify(exactly = 2) { messageRepository.insertMessage(any()) }
}
```

#### Test File Naming

- `ClassNameTest.kt` for testing `ClassName.kt`
- Place in same package structure under `test/` directory

### Git Commit Messages

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

**Types:**
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `style:` Code style changes (formatting)
- `refactor:` Code refactoring
- `test:` Adding tests
- `chore:` Build process or auxiliary tool changes

**Examples:**
```bash
feat(chat): add voice input support
fix(database): resolve migration crash
docs(readme): update setup instructions
refactor(repository): simplify message insertion
test(usecase): add tests for SendMessageUseCase
```

## Development Environment Setup

### Required Tools

1. **Android Studio** (latest stable version)
2. **Kotlin Plugin** (bundled with Android Studio)
3. **Git** for version control

### Recommended Tools

- **Android Device/Emulator** for testing
- **Detekt** for static code analysis
- **ktlint** for code formatting

### IDE Settings

**Android Studio:**
1. **Editor > Code Style > Kotlin**
   - Set from: Kotlin style guide
   
2. **Editor > Inspections**
   - Enable Kotlin inspections
   
3. **Build > Build Tools > Gradle**
   - Use Gradle JDK: 11 or later

## Project-Specific Guidelines

### Adding a New Feature

1. **Domain Layer First**
   - Define models if needed
   - Create use case
   - Define repository interface

2. **Data Layer Second**
   - Implement repository
   - Create database entities/DAOs
   - Add data mapping

3. **Presentation Layer Last**
   - Create ViewModel
   - Design UI with Compose
   - Connect to use cases

### Modifying Existing Features

1. **Check tests** - Update or add tests
2. **Check documentation** - Update if behavior changes
3. **Maintain backward compatibility** - Or document breaking changes

### Database Changes

When modifying Room entities:

1. **Increment database version** in `AurynDatabase`
2. **Provide migration** or fallback strategy
3. **Test migration** on device with existing data

```kotlin
@Database(version = 2)  // Increment version
abstract class AurynDatabase : RoomDatabase() {
    // ...
}
```

### Adding Dependencies

1. **Check necessity** - Do we really need this library?
2. **Check license** - Ensure compatible license
3. **Check size** - Consider APK size impact
4. **Update proguard rules** if needed
5. **Document reason** in PR

## Review Process

### What Reviewers Look For

1. **Functionality** - Does it work as intended?
2. **Architecture** - Follows clean architecture?
3. **Code quality** - Readable, maintainable?
4. **Tests** - Adequate test coverage?
5. **Documentation** - Clear comments and docs?
6. **Performance** - No obvious performance issues?

### Addressing Review Comments

- **Be receptive** to feedback
- **Ask questions** if unclear
- **Make changes** in new commits (don't force push)
- **Respond** to each comment
- **Mark resolved** when addressed

## Release Process

Releases are managed by project maintainers:

1. Version bump in `build.gradle.kts`
2. Update `CHANGELOG.md`
3. Create release tag
4. Generate APK/AAB
5. Publish release notes

## Getting Help

### Questions?

- **Check documentation** first (`README.md`, `ARCHITECTURE.md`)
- **Search existing issues** on GitHub
- **Create a discussion** for general questions
- **Join our community** (if available)

### Need Clarification?

Comment on the relevant issue or PR, and maintainers will respond.

## Recognition

Contributors will be:
- Listed in release notes
- Acknowledged in `CONTRIBUTORS.md` (coming soon)
- Credited in commit history

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

---

Thank you for contributing to Auryn Offline! Your efforts help make this project better for everyone. 🎉
