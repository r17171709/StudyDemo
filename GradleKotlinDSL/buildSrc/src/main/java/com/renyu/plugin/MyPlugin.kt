package com.renyu.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.tasks.PackageApplication
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("MyPlugin")

        val extension = project.extensions.create("testExtension", AndroidExtension::class.java)
        project.task("AndroidPlugin") {
            it.doLast {
                println("minSdkVersion = ${extension.minSdkVersion}")
                println("applicationId = ${extension.applicationId}")
                println("compileSdkVersion = ${extension.compileSdkVersion}")
                println("buildToolsVersion = ${extension.buildToolsVersion}")
            }
        }
        project.tasks.create("releaseInfoTask", ReleaseInfoTask::class.java)

        // 在afterEvaluate闭包中才能获取当前project中的所有task
        project.afterEvaluate {
            println("afterEvaluate")

            val mergeDebugResourcesTask = project.tasks.findByName("mergeDebugResources")
            if (mergeDebugResourcesTask != null) {
                project.tasks.create("ParseDebugTask", ParseDebugTask::class.java)
                mergeDebugResourcesTask.finalizedBy("ParseDebugTask")
                mergeDebugResourcesTask.doLast {
                    println("mergeDebugResourcesTask 执行结束")
                }
            }

            // 确保当前project是Android app project，而不是Android library project，为给定id的插件执行或注册操作
            project.plugins.withId("com.android.application") {
                val android = project.extensions.getByType(AppExtension::class.java)
                android.applicationVariants.forEach {
                    println("applicationVariant  ${it.name}")
                    it.outputs.forEach {
                        println("output  ${it.name}")
                        if (it.name.contains("debug")) {
                            project.tasks.findByName("packageDebug")?.doFirst { task ->
                                // 尝试复制图片到assets目录
                                project.projectDir.absolutePath
                                (task as PackageApplication).assets.asFile.get().path
                            }
                        }
                    }
                }
            }
        }
    }
}