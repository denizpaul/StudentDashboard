plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.monashapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.monashapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        // Fail build on lint errors (disabled initially to see all issues)
        abortOnError = false

        // Don't fail on warnings
        warningsAsErrors = false

        // Generate reports
        checkReleaseBuilds = true

        // HTML report
        htmlReport = true
        htmlOutput = file("${project.buildDir}/reports/lint/lint-report.html")

        // XML report
        xmlReport = true
        xmlOutput = file("${project.buildDir}/reports/lint/lint-report.xml")

        // SARIF report (for GitHub integration)
        sarifReport = true
        sarifOutput = file("${project.buildDir}/reports/lint/lint-report.sarif")

        // Text output to console
        textReport = true
        textOutput = file("stdout")

        // Enable all checks by default
        checkAllWarnings = true

        // Disable specific checks that might be too strict for development
        disable += setOf(
            "ObsoleteLintCustomCheck",
            "GradleDependency"
        )

        // Baseline file to suppress existing issues
        baseline = file("lint-baseline.xml")
    }
}

configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose BOM for consistent versions
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Compose dependencies
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.kotlinx.coroutines.core)

    // Compose Testing (requires BOM)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.25")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
}

kapt {
    correctErrorTypes = true
}