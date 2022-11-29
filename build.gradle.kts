plugins {
    kotlin("jvm") version "1.7.21"
    application
}

repositories.mavenCentral()

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}

application.mainClass.set("MainKt")

tasks.compileKotlin { kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.time.ExperimentalTime" }
