plugins {
    // Plugin do aplicativo Android (sem aplicar aqui, só declarando)
    id("com.android.application") version "8.5.0" apply false

    // Plugin Kotlin Android
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false

    // Plugin Hilt (se não usar Hilt, pode remover estas duas linhas)
    id("com.google.dagger.hilt.android") version "2.52" apply false
}
