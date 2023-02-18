plugins {
    id("java")
    id("maven-publish")
}

java {
    withSourcesJar()
}

group = "net.nonswag.bkt.list"
version = "1.0.3"

repositories {
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        url = uri("https://repo.thenextlvl.net/releases")
    }
    mavenCentral()
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    implementation("net.nonswag.core:core-api:2.1.2")
    annotationProcessor("net.nonswag.core:core-api:2.1.2")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.nonswag.bkt"
            artifactId = "bukkit-list"
            version = project.version.toString()
            from(components["java"])
        }
        repositories {
            maven {
                url = uri("https://repo.thenextlvl.net/releases")
                credentials {
                    username = extra["RELEASES_USER"].toString()
                    password = extra["RELEASES_PASSWORD"].toString()
                }
            }
        }
    }
}