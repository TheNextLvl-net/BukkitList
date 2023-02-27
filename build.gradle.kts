plugins {
    id("java")
    id("maven-publish")
}

java {
    withSourcesJar()
    withJavadocJar()
}

group = "net.nonswag.bkt.list"
version = "1.0.13"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.thenextlvl.net/releases")
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    implementation("net.nonswag.core:core-api:2.1.4")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
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