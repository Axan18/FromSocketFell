plugins {
  kotlin("jvm") version "2.1.21"
  id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
  implementation("io.ktor:ktor-network:3.2.1")
}

tasks.test {
  useJUnitPlatform()
}
kotlin {
  jvmToolchain(21)
}
application {
  mainClass.set("org.example.MainKt")
}
tasks.withType<JavaExec>().configureEach {
  standardInput = System.`in`
}
