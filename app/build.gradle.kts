import org.cqfn.diktat.plugin.gradle.DiktatExtension

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")
    id("org.cqfn.diktat.diktat-gradle-plugin") version "1.2.5"
    application
    checkstyle
}

repositories {
    mavenCentral() 
}

dependencies {
}

application {
    mainClass.set("demo.AppKt") 
}
diktat {
    inputs { include("app/src/**/*.kt") }
}
checkstyle {
    toolVersion = "10.3.3"
    isShowViolations = true
    isIgnoreFailures = true
}
tasks.withType<Checkstyle>().configureEach {
    reports {
        sarif.required = true
        xml.required = false
        html.required = false
    }
}