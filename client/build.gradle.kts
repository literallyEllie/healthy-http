plugins {
    id("java")
}

group = "de.elliepotato"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":"))
    implementation(project(":common"))

    // web client
    implementation("io.avaje:avaje-http-client:1.27")
}
