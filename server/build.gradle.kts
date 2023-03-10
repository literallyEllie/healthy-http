plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.elliepotato"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(project(":common"))

    // Web server
    implementation("io.javalin:javalin:5.3.2")

    implementation("io.avaje:avaje-inject:8.10")
    annotationProcessor("io.avaje:avaje-inject-generator:8.10")
    annotationProcessor("io.avaje:avaje-http-javalin-generator:1.27")

    // Logging
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.6")
}

tasks {
    shadowJar {
        archiveFileName.set("app.jar")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.elliepotato.hhttp.Main"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
