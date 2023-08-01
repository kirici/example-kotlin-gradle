plugins {
    kotlin("jvm") version "1.5.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass.set("HelloWorldKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "HelloWorldKt"
    }
}

tasks.create("fatJar", Jar::class) {
    archiveClassifier.set("fat")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA") // Exclude signature files
    }
    manifest {
        attributes["Main-Class"] = "HelloWorldKt" // Specify the main class for the fat jar
    }
    with(tasks.jar.get())
}
