plugins {
    id("java")
}

group = "de.elliepotato.hhttp"
version = "1.0.0-SNAPSHOT"

dependencies {
    api(project(":common"))

    // web client
    implementation("io.avaje:avaje-http-client:1.31")
}

publishing {
    publishing {
        publications.create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}