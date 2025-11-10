plugins {
    alias(libs.plugins.nowinandroid.android.library)
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.kinopoisk.core.base"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}