// Arquivo: build.gradle.kts (RAIZ)

plugins {
    // Plugins aplicados somente aos módulos
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
