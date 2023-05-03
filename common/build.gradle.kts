plugins {
    id("java")
}

group = "de.elliepotato.hhttp"
version = "1.0.0-SNAPSHOT"

publishing {
    publishing {
        publications.create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories.maven {
        name = "skirmish-github"
        url = uri("https://maven.pkg.github.com/skirmishnet/${project.name}")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}