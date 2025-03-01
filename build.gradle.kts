plugins {
    id("java")
    id("com.diffplug.spotless") version "7.0.2"
}

group = "one.jackmyers"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.stefanbirkner:system-lambda:1.2.1")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.jetbrains:annotations:24.0.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    java {
        importOrder()
        removeUnusedImports()
        cleanthat()
        googleJavaFormat()
        formatAnnotations()
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
