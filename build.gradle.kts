plugins {
    kotlin("jvm") version "1.9.21"
    application
}

repositories.mavenCentral()

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}

application.mainClass.set("MainKt")
