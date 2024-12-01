plugins {
    kotlin("jvm") version "2.1.0"
}

group = "io.github.hemeroc"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
