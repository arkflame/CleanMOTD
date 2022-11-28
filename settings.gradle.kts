rootProject.name = "cleanmotd-parent"

listOf("spigot", "velocity", "bungee", "jar").forEach {
    include("cleanmotd-$it")
    project(":cleanmotd-$it").projectDir = file(it)
}