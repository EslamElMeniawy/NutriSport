rootProject.name = "NutriSport"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":shared")
include(":navigation")
include(":di")
include(":data")
include(":feature:auth")
include(":feature:home")
include(":feature:home:products_overview")
include(":feature:home:cart")
include(":feature:home:categories")
include(":feature:home:categories:category_search")
include(":feature:product_details")
include(":feature:profile")
include(":feature:admin_panel")
include(":feature:admin_panel:manage_product")
