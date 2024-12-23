pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()

    }
}

rootProject.name = "Forecast"
include(":app")
include(":core")
include(":features")
include(":data")
include(":features:city_input")
include(":features:current_weather")
include(":features:forecast")
include(":shared-ui")
include(":weather-lib")
