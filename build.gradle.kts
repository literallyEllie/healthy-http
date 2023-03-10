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
        implementation("io.avaje:avaje-http-api:1.27")
        implementation("io.avaje:avaje-config:2.4")

        // json serialization
        implementation("io.avaje:avaje-jsonb:1.2")
        annotationProcessor("io.avaje:avaje-jsonb-generator:1.2")

        // https://mvnrepository.com/artifact/org.jetbrains/annotations
        implementation("org.jetbrains:annotations:24.0.0")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testImplementation("org.mockito:mockito-core:5.1.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

