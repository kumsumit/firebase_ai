import com.android.build.api.dsl.LibraryExtension
import com.android.Version
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "io.flutter.plugins.firebase.ai"
version = "1.0-SNAPSHOT"

plugins {
    id("com.android.library")
}

apply(from = "local-config.gradle.kts")

val compileSdkValue = extra["compileSdk"] as Int
val minSdkValue = extra["minSdk"] as Int
val javaVersion = extra["javaVersion"] as JavaVersion

val agpMajor = Version.ANDROID_GRADLE_PLUGIN_VERSION.substringBefore('.').toInt()
val builtInKotlin =
    providers.gradleProperty("android.builtInKotlin")
        .map(String::toBoolean)
        .orElse(agpMajor >= 9)
        .get()

if (agpMajor < 9 || !builtInKotlin) {
    apply(plugin = "org.jetbrains.kotlin.android")
}

repositories {
    google()
    mavenCentral()
}

extensions.configure<LibraryExtension>("android") {
    namespace = "io.flutter.plugins.firebase.ai"
    compileSdk = compileSdkValue

    defaultConfig {
        minSdk = minSdkValue
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    sourceSets {
        named("main") {
            java.directories.add("src/main/kotlin")
        }
    }

    lint {
        disable += "InvalidPackage"
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(javaVersion.majorVersion))
        }
    }
}
