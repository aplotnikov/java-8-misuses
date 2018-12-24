plugins {
    java

    id("project-report")
    id("build-dashboard")

    id("net.ltgt.apt") version "0.19"
}

group = "io.github.aplotnikov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val lombokVersion = "1.18.4"

dependencies {
    compileOnly("org.projectlombok:lombok:${lombokVersion}")

    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}