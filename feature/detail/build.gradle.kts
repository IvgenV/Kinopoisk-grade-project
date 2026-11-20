plugins {
    alias(libs.plugins.nowinandroid.android.feature.impl)
    alias(libs.plugins.nowinandroid.android.library.compose)
    //alias(libs.plugins.nowinandroid.hilt)
}

android {
    namespace = "com.example.kinopoisk.feature.detail"

    testOptions {
        // DetailsScreenViewModel calls android.util.Log in its error handler
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.base)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.adaptive.navigation3)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
}