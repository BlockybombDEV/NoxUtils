import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "me.iris"
version = "2.2.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.9.24"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://maven.noxcrew.com/public") {
        name = "Noxcrew Public Maven Repository"
    }
    maven(url = uri("https://repo.codemc.org/repository/maven-public/"))
}

dependencies {
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")
    compileOnly("dev.jorel:commandapi-bukkit-core:9.5.2")
    implementation("fr.skytasul:glowingentities:1.3.5")
    implementation("fr.skytasul:glowingentities:1.3.5")
    implementation("com.noxcrew.noxesium:api:2.4.0-SNAPSHOT")
    implementation("com.noxcrew.noxesium:paper:2.4.0-SNAPSHOT")
    implementation("dev.jorel:commandapi-bukkit-kotlin:9.5.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = 21.toString()
            freeCompilerArgs += listOf("-Xexplicit-api=strict")
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "me.iris.noxUtils.NoxUtils"
    }

    // To avoid the duplicate handling strategy error
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // To add all of the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}