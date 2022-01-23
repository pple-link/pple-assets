import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    base
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa") apply false
    kotlin("kapt")

    id("com.google.cloud.tools.jib") version "3.1.4" apply false

    jacoco
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "link.pple"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")

    apply(plugin = "jacoco")

    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        val springCloudVersion: String by project
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")

        implementation("com.squareup.okhttp3:okhttp:4.9.3")

        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("mysql:mysql-connector-java")

        implementation("org.apache.tika:tika-core:2.2.1")

        implementation("com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:1.0.6")

        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
        testImplementation("io.mockk:mockk:1.12.1")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("io.strikt:strikt-core:0.33.0")
    }

    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.test {
        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(file("$buildDir/jacoco/jacoco.exec"))
        }

        finalizedBy("jacocoTestReport")
    }

    jacoco {
        toolVersion = "0.8.7"
    }

    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
        finalizedBy("jacocoTestCoverageVerification")

        classDirectories.setFrom(
            fileTree(project.buildDir) {
                exclude(
                    "**/Q*.*",
                    "**/*Test.*"
                )
                include(
                    "**/classes/**/main/**"
                )
            }
        )
    }
}
