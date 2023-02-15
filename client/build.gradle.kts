plugins {
    id("java")
}

group = "de.elliepotato"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(project(":common"))

    // web client
    implementation("io.avaje:avaje-http-client:1.27")
}
