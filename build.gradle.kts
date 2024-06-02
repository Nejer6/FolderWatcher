plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "com.github.nejer6"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}

tasks.register<Jar>("fatJar") {
    group = "build"
    description = "Assembles a fat JAR including all dependencies."

    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    archiveFileName.set("my-application-all.jar")
}
