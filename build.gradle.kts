import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0"
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.astrog"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {

    api(project(":lib:telegram-common"))
    api(project(":lib:openai-api"))

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter")
    //implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

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
}