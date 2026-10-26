// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()

        // This is used only for internal Google builds.
        maven { url = uri("../nowinandroid-prebuilts/m2repository") }
    }
//    dependencies {
//        classpath(libs.google.oss.licenses.plugin) {
//            exclude(group = "com.google.protobuf")
//        }
//    }

}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.dependencyGuard) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.google.osslicenses) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.room) apply false
}