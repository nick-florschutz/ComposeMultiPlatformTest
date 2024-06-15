import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // SDP & SSP pixel density library - https://github.com/ChainTechNetwork/sdp-ssp-compose-multiplatform
            implementation(libs.sdp.ssp.compose.multiplatform)

            // Multi-platform date and time picker - https://github.com/ChainTechNetwork/compose_multiplatform_date_time_picker
            implementation(libs.kmp.date.time.picker)

            // Parcelize - https://github.com/05nelsonm/component-parcelize
            implementation(libs.parcelize)

            // Coroutines - https://github.com/Kotlin/kotlinx.coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Date & Time - https://github.com/Kotlin/kotlinx-datetime
            implementation(libs.kotlinx.datetime)

            /********* Firebase - https://github.com/gitliveapp/firebase-kotlin-sdk/ *********/
            // Firebase Crashlytics
            implementation(libs.gitlive.firebase.crashlytics)
            // Firebase Auth
            implementation(libs.gitlive.firebase.auth)
            // Firebase Firestore
            implementation(libs.gitlive.firebase.firestore)
            // Firebase Storage
//            implementation("dev.gitlive:firebase-storage:1.12.0")
            // Firebase Installations
            implementation(libs.gitlive.firebase.installations)
            /*********************************************************************************/

            // Kotlin Serialization - https://github.com/Kotlin/kotlinx.serialization
            implementation(libs.kotlinx.serialization.json)

            // Multi screen & window size support - https://github.com/chrisbanes/material3-windowsizeclass-multiplatform
            implementation(libs.multi.screensize.support)

            // Multi-platform Constraint Layout - https://github.com/Lavmee/constraintlayout-compose-multiplatform/
            implementation(libs.constraintlayout.compose.multiplatform)

        }
    }
}

android {
    namespace = "kmp.project.multiplatform_demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "kmp.project.multiplatform_demo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

