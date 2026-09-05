@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.loom)
}

loom {
    accessWidenerPath = file("src/main/resources/orbit.accesswidener")
}

repositories {
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    maven("https://maven.teamresourceful.com/repository/maven-public/")
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.loader.fabric)
    implementation(libs.loader.kotlin)
    implementation(libs.fapi)

    implementation(libs.resourcefullib)
    include(libs.resourcefullib)
    implementation(libs.olympus)
    include(libs.olympus)
    implementation(libs.modmenu)

    runtimeOnly(libs.devauth)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

val targetJavaVersion = 25

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

kotlin {
    jvmToolchain(targetJavaVersion)
}

base {
    archivesName.set(project.property("archives_base_name") as String)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    withSourcesJar()
}
