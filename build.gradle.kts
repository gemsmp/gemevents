import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.0"
}

group = "de.mcmdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "lucko"
        url = uri("https://repo.lucko.me/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnly("me.lucko:helper:5.6.14")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}

paperPluginYaml {
    name = "gemevents"
    main = "de.mcmdev.gemevents.GemEvents"
    author = "mcmdev"
    apiVersion = "1.21"
    dependencies {
        bootstrap {
            register("helper") {
                load = PaperPluginYaml.Load.BEFORE
                required = true
            }
        }
        server {
            register("helper") {
                load = PaperPluginYaml.Load.BEFORE
                required = true
            }
        }
    }
}