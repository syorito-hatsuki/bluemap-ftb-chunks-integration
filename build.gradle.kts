plugins {
    id("fabric-loom")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}

val modVersion: String by project
version = modVersion

val mavenGroup: String by project
group = mavenGroup

repositories {
    maven {
        name = "CurseMaven"
        setUrl("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }

    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

dependencies {
    //FOR SERVER STARTING
    implementation("io.netty", "netty-all", "4.1.79.Final")

    val minecraftVersion: String by project
    minecraft("com.mojang", "minecraft", minecraftVersion)
    
    val yarnMappings: String by project
    mappings("net.fabricmc", "yarn", yarnMappings, null, "v2")
    
    val loaderVersion: String by project
    modImplementation("net.fabricmc", "fabric-loader", loaderVersion)
    
    val fabricVersion: String by project
    modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)
    //Deprecated libs for BlueMap
//    modImplementation("net.fabricmc.fabric-api", "fabric-api-deprecated", fabricVersion)

    val fabricKotlinVersion: String by project
    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricKotlinVersion)

    val blueMapVersion: String by project
    modCompileOnly("maven.modrinth", "BlueMap", blueMapVersion)

    val ftbChunksVersion: String by project
    modCompileOnly("curse.maven", "ftbchunks-472657", ftbChunksVersion)

    val ftbLibraryVersion: String by project
    modCompileOnly("curse.maven", "ftblibrary-438495", ftbLibraryVersion)

    val ftbTeamsVersion: String by project
    modCompileOnly("curse.maven", "ftbteams-438497", ftbTeamsVersion)
}

tasks {
    val javaVersion = JavaVersion.VERSION_17

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions { jvmTarget = javaVersion.toString() } }

    jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.version)) }
    }

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.toString())) }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
