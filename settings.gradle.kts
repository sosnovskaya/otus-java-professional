rootProject.name = "otusJava"
include("hw01-gradle")
include("hw31-executors")
include("hw31-executors")
include ("hw32-thread-safe-collections:QueueDemo")

pluginManagement {
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
    }
}