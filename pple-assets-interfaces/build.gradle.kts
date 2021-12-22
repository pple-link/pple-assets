import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.google.cloud.tools.jib")
}

dependencies {
    implementation(project(":pple-assets-domain"))
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:jdk-11.0.10_9-debian"
    }
    to {
        image = "pple-assets"
        tags = if ("prod" == System.getenv("ENV")) {
            setOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
        } else {
            setOf("latest")
        }
    }
    container {
        ports = listOf("10002")
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
    extraDirectories {
        setPaths("/pple/logs/app")
    }
}
