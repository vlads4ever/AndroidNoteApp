// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    // To use Kotlin Symbol Processing (KSP)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}

// Hilt
buildscript {
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
    }
}