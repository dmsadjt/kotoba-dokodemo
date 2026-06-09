import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
    implementation(libs.koin.core)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.sqldelight.jvm.driver)
}

compose.desktop {
    application {
        mainClass = "com.dmsadjt.kotoba.MainKt"
        jvmArgs += listOf("--add-opens", "java.base/java.sql=ALL-UNNAMED")
        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.dmsadjt.kotoba"
            packageVersion = "1.0.0"
        }
    }
}