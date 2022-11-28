plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2" apply true
}

dependencies {
    shadow(project(":cleanmotd-spigot"))
    shadow(project(":cleanmotd-bungee"))
}



tasks {
    shadowJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("CleanMotd.jar")
        configurations = listOf(project.configurations.shadow.get())

        arrayOf("velocity").forEach {
            val buildTask = project(":cleanmotd-$it").tasks.named("jar")
            dependsOn(buildTask)

            from(zipTree(buildTask.map {out -> out.outputs.files.singleFile}))
        }
    }

    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(8)
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}