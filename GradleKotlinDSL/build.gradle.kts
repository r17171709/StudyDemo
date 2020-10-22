// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.10")
    repositories {
        google()
        jcenter()
    }

    project.apply {
        from("config.gradle.kts")
    }
    // 通过代理获得
    val kotlinVersion: String by extra
    println("kotlinVersion : $kotlinVersion")

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath(kotlin("android-extensions", kotlinVersion))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}