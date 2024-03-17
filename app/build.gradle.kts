import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

version = "PRE-0.5"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(project(":lang"))

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    implementation("org.jetbrains:annotations:24.1.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    // Define the main class for the application.
    mainClass = "pl.moderr.eduscript.app.App"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("eduscript")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "pl.moderr.eduscript.app.App"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}