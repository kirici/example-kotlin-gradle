import org.cqfn.diktat.plugin.gradle.DiktatExtension

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")
    id("org.cqfn.diktat.diktat-gradle-plugin") version "1.2.5"
    application
}

repositories {
    mavenCentral() 
}

diktat {
    inputs {
        include("src/**/*.kt")
    }
    ignoreFailures = true
    reporter = "sarif"
    output = "diktat-report.sarif"
}

dependencies {
}

application {
    mainClass.set("demo.AppKt") 
}
