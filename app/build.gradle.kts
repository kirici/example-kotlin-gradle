plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")

    application 
}

repositories {
    mavenCentral() 
}

dependencies {
}

application {
    mainClass.set("demo.AppKt") 
}