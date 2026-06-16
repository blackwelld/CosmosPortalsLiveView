plugins {
    id "net.neoforged.gradle" version "6.0.52"
}

version = "1.20.1-1.0.0"
group = "com.blackwell.cosmosportalsliveview"

base {
    archivesName = "CosmosPortalsLiveView"
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

repositories {
    mavenCentral()
    maven {
        name = "NeoForged"
        url = "https://maven.neoforged.net/releases"
    }
    maven {
        name = "CosmosLib"
        url = "https://maven.theccosmos.com/releases"
    }
}

dependencies {
    implementation "net.neoforged:neoforge:20.1.91"
    compileOnly "com.tcn.cosmoslibrary:cosmoslibrary:1.20.1-3.0.0"
    compileOnly "com.tcn.cosmosportals:cosmosportals:1.20.1-1.0.0"
}

tasks.withType(JavaCompile).configureEach {
    source.includes.add "**/*.java"
}
