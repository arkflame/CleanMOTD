plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.6.0")
}

bukkit {
    name = "CleanMotd"
    main = "dev._2lstudios.cleanmotd.bukkit.CleanMOTD"
    apiVersion = "1.13"
    website = project.property("url")!! as String
    description = project.description as String
    authors = listOf("2LS")
    softDepend = listOf("ProtocolLib")
    version = project.version as String
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8
