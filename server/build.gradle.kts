plugins {
    id("java")
}

group = "de.elliepotato"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":"))
    implementation(project(":common"))

    // Web server
    implementation("io.javalin:javalin:5.3.2")

    implementation("io.avaje:avaje-inject:8.10")
    implementation("io.avaje:avaje-config:2.4")
    annotationProcessor("io.avaje:avaje-inject-generator:8.10")
    annotationProcessor("io.avaje:avaje-http-javalin-generator:1.27")

    // Logging
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.6")
}
