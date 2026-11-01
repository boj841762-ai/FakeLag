plugins {
    idea
    java
    id("gg.essential.loom") version "0.10.0.+"
    id("dev.architectury.architectury-pack200") version "0.1.3"
}

val baseGroup: String by project
val mcVersion: String by project
val modid: String by project
val modName: String by project
version = project.property("version") as String
group = baseGroup

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    log4jConfigs.from(file("log4j2.xml"))
    runConfigs {
        "client" {
            // Оптимизированные флаги JVM для клиента
            vmArgs.addAll(listOf(
                "-Xms1G", "-Xmx3G",
                "-XX:+UseG1GC", "-XX:+UnlockExperimentalVMOptions",
                "-XX:G1NewSizePercent=20", "-XX:G1ReservePercent=20",
                "-XX:MaxGCPauseMillis=50", "-XX:G1HeapRegionSize=32M",
                "-XX:+DisableExplicitGC", "-XX:+AlwaysPreTouch",
                "-XX:+ParallelRefProcEnabled"
            ))
            if (org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX) {
                vmArgs.remove("-XstartOnFirstThread")
            }
        }
        remove(getByName("server"))
    }
    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
    }
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    // Любые Forge 1.8.9 моды, положенные в папку mods/, автоматически
    // подключаются к клиенту при запуске через run.bat (loom ремапит их).
    fileTree("mods") { include("*.jar") }.forEach { modRuntimeOnly(files(it)) }
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(Jar::class) {
    manifest.attributes.run {
        this["FMLCorePluginContainsFMLMod"] = "true"
        this["ForceLoadAsMod"] = "true"
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("mcversion", mcVersion)
    inputs.property("modid", modid)
    inputs.property("modname", modName)
    filesMatching(listOf("mcmod.info")) {
        expand(inputs.properties)
    }
}

tasks.remapJar {
    archiveClassifier.set("")
}
tasks.jar {
    archiveClassifier.set("dev")
    destinationDirectory.set(layout.buildDirectory.dir("devlibs"))
}
