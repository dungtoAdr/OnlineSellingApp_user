pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io" )
        maven(url="https://maven.aliyun.com/repository/jcenter")
    }
}

rootProject.name = "AppBanHang1"
include(":app")
 