import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "io.spring.dependency-management")
}

subprojects {
    tasks.withType<BootRun>{
        enabled = false
    }

    tasks.withType<BootJar>{
        enabled = false
    }
}

group = "com.astrog.telegrambot"
version = "0.2"

dependencies {

    implementation(project(":lib:telegram-common"))
    implementation(project(":lib:openai-api"))
    implementation(project(":lib:audioconverter"))

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.bootJar {
    archiveFileName.set("app.jar")
    launchScript()
}

tasks.jar {
    enabled = false
}