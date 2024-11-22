import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
}

kotlin {
    tasks.create("testClasses")

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies {
                implementation(libs.core.ktx)
                implementation(libs.compose.ui.test.junit4.android)
                implementation(libs.compose.ui.test.manifest)
            }
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

        tasks["compileKotlinIosArm64"].dependsOn("kspCommonMainKotlinMetadata")
        tasks["compileKotlinIosSimulatorArm64"].dependsOn("kspCommonMainKotlinMetadata")
        tasks["compileKotlinIosX64"].dependsOn("kspCommonMainKotlinMetadata")
    }

    sourceSets {

        iosMain {
            kotlin.srcDir("build/generated/ksp/metadata")
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // CameraX - https://developer.android.com/jetpack/androidx/releases/camera
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
            implementation(libs.kotlinx.coroutines.guava)
            implementation(libs.androidx.camera.camera2)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
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
            api(libs.gitlive.firebase.kotlin.crashlytics)
            implementation(libs.gitlive.firebase.common)
            // Firebase Auth
            implementation(libs.gitlive.firebase.auth)
            // Firebase Firestore
            implementation(libs.gitlive.firebase.firestore)
            // Firebase Storage
//            implementation("dev.gitlive:firebase-storage:1.12.0")
            // Firebase Installations
//            implementation(libs.gitlive.firebase.installations)
            /*********************************************************************************/

            // Kotlin Serialization - https://github.com/Kotlin/kotlinx.serialization
            implementation(libs.kotlinx.serialization.json)

            // Multi screen & window size support - https://github.com/chrisbanes/material3-windowsizeclass-multiplatform
            implementation(libs.multi.screensize.support)

            // Multi-platform Constraint Layout - https://github.com/Lavmee/constraintlayout-compose-multiplatform/
            implementation(libs.constraintlayout.compose.multiplatform)

            // AAY Charts & Graphs for Multiplatform - https://github.com/TheChance101/AAY-chart
            implementation(libs.aay.chart)

            // Room database
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            // Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.koin.integration)

            // Koin
            implementation(libs.koin.compose)
            runtimeOnly(libs.koin.core.coroutines)
            runtimeOnly(libs.koin.core)
            runtimeOnly(libs.koin.test)
            runtimeOnly(libs.koin.test.junit4)

            // datastore
            implementation(libs.datastore.preferences)
            implementation(libs.atomicfu)

            // Moko Permissions
////            implementation(libs.permissions)
            implementation(libs.moko.permissions.compose) // permissions api + compose extensions
            implementation(libs.moko.permissions.test)

            // Konnectivity - https://github.com/plusmobileapps/konnectivity
            implementation(libs.konnectivity)

            // Napier Logging - https://github.com/AAkira/Napier
            implementation(libs.napier)

        }
        commonTest {
            dependencies {
//                implementation(libs.kotlin.test)
                implementation(kotlin("test-annotations-common"))
                implementation(libs.assertk)

                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
            }
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
}