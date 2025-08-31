import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.serialization)
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
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.android.client)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin.client)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.serialization)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.multiplatform.settings.make.observable)
            implementation(libs.coil3)
            implementation(libs.coil3.compose)
            implementation(libs.coil3.compose.core)
            implementation(libs.coil3.network.ktor)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "elmeniawy.eslam.nutrisport.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    testOptions {
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
}

buildkonfig {
    packageName = "elmeniawy.eslam.nutrisport.shared"

    defaultConfigs {
        val webClientId: String = gradleLocalProperties(rootDir, providers).getProperty("WEB_CLIENT_ID")
        val paypalClientId: String = gradleLocalProperties(rootDir, providers).getProperty("PAYPAL_CLIENT_ID")
        val paypalSecretId: String = gradleLocalProperties(rootDir, providers).getProperty("PAYPAL_SECRET_ID")

        require(webClientId.isNotEmpty()) {
            "Register your web client id from developer and place it in local.properties as `WEB_CLIENT_ID`"
        }

        require(paypalClientId.isNotEmpty()) {
            "Register your paypal client id from developer and place it in local.properties as `PAYPAL_CLIENT_ID`"
        }

        require(paypalSecretId.isNotEmpty()) {
            "Register your paypal secret id from developer and place it in local.properties as `PAYPAL_SECRET_ID`"
        }

        buildConfigField(FieldSpec.Type.STRING, "WEB_CLIENT_ID", webClientId)
        buildConfigField(FieldSpec.Type.STRING, "PAYPAL_CLIENT_ID", paypalClientId)
        buildConfigField(FieldSpec.Type.STRING, "PAYPAL_SECRET_ID", paypalSecretId)
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}