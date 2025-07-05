   import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
   import kotlinx.html.link
   import java.lang.module.ModuleFinder.compose

   plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.indusjs.statustracker"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")

            head.apply {
                add {
                    link(href = "/styles.css", rel = "stylesheet")
                }
            }
        }
        server {
            remoteDebugging {
                enabled.set(true)
                port.set(5005) // You can choose a different port if 5005 is in use
            }
        }

    }
}

kotlin {
    // This example is frontend only. However, for a fullstack app, you can uncomment the includeServer parameter
    // and the `jvmMain` source set below.
    configAsKobwebApplication("statustracker" /*, includeServer = true*/)

    sourceSets {
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here if building a fullstack app
//        }

        jsMain.dependencies {
//            implementation(libs.compose.material3)
            // Add the Material3 dependency here:
//            implementation("org.jetbrains.compose.material3:material3:1.6.1")
//            implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-material-js")
//            implementation(kotlinWrappers.emotion)
            implementation("com.varabyte.kobweb:compose-html-ext:...")
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.core.js)
            implementation(libs.kobweb.silk)
            implementation(libs.kobweb.silk.js)
            implementation(libs.silk.foundation)
            implementation(libs.kobweb.silk.widgets)
            // This default template uses built-in SVG icons, but what's available is limited.
            // Uncomment the following if you want access to a large set of font-awesome icons:
             implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            /////////
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        // Uncomment the following if you pass `includeServer = true` into the `configAsKobwebApplication` call.
//        jvmMain.dependencies {
//            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
//        }
    }
}
