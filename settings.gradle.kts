rootProject.name = "common"

dependencyResolutionManagement {
    repositories {
        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
            from("gr.alx:versions:1.0")
        }
    }
}
