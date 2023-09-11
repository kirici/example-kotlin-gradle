import org.cqfn.diktat.plugin.gradle.DiktatExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")
    id("org.cqfn.diktat.diktat-gradle-plugin") version "1.2.5"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
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
    output = "build/reports/diktat/diktat.sarif"
}

tasks.check {
    dependsOn(tasks.diktatCheck)
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.SARIF)
    }
    filter {
        include("src/**/*.kt")
    }
}

dependencies {
    // ktlintRuleset("com.github.username:rulseset:main-SNAPSHOT")
    // ktlintRuleset(files("/path/to/custom/rulseset.jar"))
}

application {
    mainClass.set("demo.AppKt") 
}
