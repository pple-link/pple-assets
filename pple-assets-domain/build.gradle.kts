plugins {
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    kotlin("plugin.jpa")
}

noArg {
    annotations(
        "javax.persistence.Entity",
        "javax.persistence.MappedSuperclass",
        "javax.persistence.Embeddable"
    )
}

allOpen {
    annotations(
        "javax.persistence.Entity",
        "javax.persistence.MappedSuperclass",
        "javax.persistence.Embeddable"
    )
}

dependencies {
    api(project(":pple-assets-infrastructure"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
}
