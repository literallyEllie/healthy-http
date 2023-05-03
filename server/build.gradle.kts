plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.elliepotato.hhttp"
version = "1.0.0-SNAPSHOT"

dependencies {
    api(project(":common"))

    // Web server
    implementation("io.javalin:javalin:5.4.2")

    implementation("io.avaje:avaje-inject:9.1-RC2")
    annotationProcessor("io.avaje:avaje-inject-generator:9.1-RC2")
    annotationProcessor("io.avaje:avaje-http-javalin-generator:1.31")

    // Logging
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.6")
}

publishing {
    publishing {
        publications.create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
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
