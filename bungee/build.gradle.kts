plugins {
    java
    id("net.minecrell.plugin-yml.bungee") version "0.5.2"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.16-R0.5-SNAPSHOT")
}

bungee {
    name = "CleanMOTD"
    main = "dev._2lstudios.cleanmotd.bungee.CleanMOTD"
    version = project.version as String
    description = project.description
    author = "2LS"
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8