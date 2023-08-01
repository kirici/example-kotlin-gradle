plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("io.gitlab.arturbosch.detekt").version("1.23.1")

    application 
}

repositories {
    mavenCentral() 
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5") 

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2") 

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.google.guava:guava:31.1-jre") 
}

application {
    mainClass.set("demo.AppKt") 
}

tasks.named<Test>("test") {
    useJUnitPlatform() 
}