plugins {
    id("java")
}

group = "de.elliepotato"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":"))
    implementation(project(":common"))

    // web client
    implementation("io.avaje:avaje-config:1.2.6")
}
