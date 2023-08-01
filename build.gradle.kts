plugins {
    kotlin("jvm") version "1.5.21"
    application
}

repositories {
    mavenCentral()
}

val detekt by configurations.creating

val detektTask = tasks.register<JavaExec>("detekt") {
    setIgnoreExitValue(true)
    main = "io.gitlab.arturbosch.detekt.cli.Main"
    classpath = detekt

    val input = projectDir
    val exclude = ".*/build/.*,.*/resources/.*"
    val sarif = "sarif:build/reports/detekt.sarif"
    val basepath = "."
    val params = listOf("-i", input, "-ex", exclude, "-r", sarif, "--base-path", basepath)

    args(params)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.23.1")
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
    dependsOn(detektTask)
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
