plugins {
    id("java")
}

group = "de.elliepotato"
version = "1.0.0-SNAPSHOT"

subprojects {
    apply(plugin = "java")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        // http
        implementation("io.avaje:avaje-http-api:1.39")
        implementation("io.avaje:avaje-config:3.2")

        // https://mvnrepository.com/artifact/com.google.code.gson/gson
        implementation("com.google.code.gson:gson:2.10.1")

        // https://mvnrepository.com/artifact/org.jetbrains/annotations
        implementation("org.jetbrains:annotations:24.0.1")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testImplementation("org.mockito:mockito-core:5.1.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

