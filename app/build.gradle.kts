import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.common.compose14"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.common.compose14"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // 签名配置
    signingConfigs {
        create("release") {
            storeFile = file("./keystore/huajianghusign")
            storePassword = "wdy110223"
            keyAlias = "hjhregister"
            keyPassword = "wdy110223n"
        }

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //状态栏导航栏
//    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.33.0-alpha")
    //compsoebanner
    implementation ("com.github.zhujiang521:Banner:2.6.5")
    // Retrofit网络库
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    //compose使用viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    //图片加载框架
    implementation("io.coil-kt:coil-compose:2.4.0")
    //通用loading
    implementation("com.github.commandiron:ComposeLoading:1.0.4")
    //上拉下拉刷新库
    implementation ("io.github.loren-moon:composesmartrefresh:1.2.1")
    //日志
    implementation ("com.orhanobut:logger:2.2.0")
    //lerp
    implementation("androidx.compose.ui:ui-util")
    //约束布局
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    //播放器
    //事件总成
    implementation ("com.github.biubiuqiu0:flow-event-bus:1.0.1")
}

