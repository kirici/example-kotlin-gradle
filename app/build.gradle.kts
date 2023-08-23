plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")

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

checkstyle {
    toolVersion = "10.3.3"
    isShowViolations = true
    isIgnoreFailures = true
}
tasks.withType<Checkstyle>().configureEach {
    reports {
        sarif.required = true
        xml.required = false
        html.required = fase
    }
}