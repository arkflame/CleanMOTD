plugins {
    java
    id("net.kyori.blossom") version "1.2.0"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(11)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))

blossom {
    replaceTokenIn("src/main/java/dev/_2lstudios/cleanmotd/velocity/CleanMOTD.java")
    replaceToken("{version}", project.version)
    replaceToken("{description}", project.description)
}