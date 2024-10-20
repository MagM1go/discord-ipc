plugins {
    application

    kotlin("plugin.serialization") version "2.0.20"
    kotlin("jvm") version "2.0.20"
}

group = "ru.magmigo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:${property("ktor_version")}")
    implementation("io.ktor:ktor-client-cio:${property("ktor_version")}")
    implementation("io.ktor:ktor-client-websockets:${property("ktor_version")}")
    implementation("io.ktor:ktor-client-cio-jvm:${property("ktor_version")}")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

kotlin {
    jvmToolchain(21)
    explicitApi()
}