// build.gradle.kts (raiz) - exemplo mínimo
plugins {
    // Declare plugins com 'apply false' aqui se quiser centralizar versões, ex:
    // id("com.android.application") version "8.1.0" apply false
    // kotlin("android") version "1.9.10" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
