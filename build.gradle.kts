import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension

plugins {
    kotlin("jvm") version "1.8.21"

    id("org.jetbrains.kotlin.plugin.noarg") version "1.8.21"

    application
}

group = "org.hhh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:6.2.4.Final")

    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc:11.2.3.jre17")

    testImplementation(kotlin("test"))
}

configure<NoArgExtension> {
    annotation("jakarta.persistence.Entity")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "17"
    }
}
