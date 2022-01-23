import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.google.cloud.tools.jib")
}

dependencies {
    api(project(":pple-assets-domain"))

    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.1.0")
    implementation("org.springframework.cloud:spring-cloud-starter-aws-secrets-manager-config:2.2.6.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:jdk-11.0.10_9-debian"
    }
    to {
        image = "pple_assets_batch"
        tags = setOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), "latest")
    }
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}

val jar: Jar by tasks
jar.enabled = false

tasks.withType<BootJar> {
    enabled = true
    archiveFileName.set("app.jar")
}

