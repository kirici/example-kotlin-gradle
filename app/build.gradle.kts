import org.cqfn.diktat.plugin.gradle.DiktatExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import de.undercouch.gradle.tasks.download.Download

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")
    id("org.cqfn.diktat.diktat-gradle-plugin") version "1.2.5"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    id("de.undercouch.download") version "5.5.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // ktlintRuleset("com.github.username:rulseset:main-SNAPSHOT")
    // ktlintRuleset(files("/path/to/custom/rulseset.jar"))
}

val getSonarZip by tasks.creating(Download::class) {
    src("https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-5.0.1.3006.zip")
    dest(layout.buildDirectory.file("sonar-scanner-cli-5.0.1.3006.zip"))
}

tasks.register<Copy>("setupSonar") {
    dependsOn(getSonarZip)
    from(zipTree(getSonarZip.dest))
    into(layout.buildDirectory)
}

diktat {
    inputs {
        include("src/**/*.kt")
    }
    ignoreFailures = true
    reporter = "sarif"
    output = "build/reports/diktat/diktat.sarif"
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

detekt {
    // basePath = projectDir.toString()
    reports.xml.required.set(true)
    reports.sarif.required.set(true)
}

application {
    mainClass.set("demo.AppKt")
}

val tasksToDependOnSetupSonar = listOf(
    "ktlintKotlinScriptCheck",
    "ktlintMainSourceSetCheck",
    "runKtlintCheckOverKotlinScripts",
    "runKtlintCheckOverMainSourceSet",
    "distTar",
    "jar"
)

tasks.check {
    dependsOn(tasks.diktatCheck)
}

tasksToDependOnSetupSonar.forEach { taskName ->
    tasks.named(taskName) {
        dependsOn("setupSonar")
    }
}

tasks.register("execSonar") {
    dependsOn(getSonarZip, "setupSonar")
    doLast {
        rootProject.exec {
            commandLine("${projectDir}/build/sonar-scanner-5.0.1.3006/bin/sonar-scanner")
        }
    }
}

tasks.named("build") {
    dependsOn("execSonar")
}
