// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    with(libs.plugins) {
        alias(android.application) apply false
        alias(jetbrains.kotlin.android) apply false
        alias(hilt.androidPlugin) apply false
    }
}

buildscript {
    extra.apply {
        set("compileSdk", 34)
        set("minSdk", 28)
        set("targetSdk", 34)
    }
}