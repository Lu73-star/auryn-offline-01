// Arquivo: build.gradle.kts (RAIZ)

plugins {
    // Plugin do Android e Kotlin serão aplicados somente nos módulos (como app/)
    // Aqui usamos o version catalog automático do Gradle moderno
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
