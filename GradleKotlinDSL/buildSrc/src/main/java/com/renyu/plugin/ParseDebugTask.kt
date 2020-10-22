package com.renyu.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ParseDebugTask : DefaultTask() {
    @TaskAction
    fun doAction() {

        val file =
            "${project.buildDir.path + "/intermediates/merged_manifests/debug/AndroidManifest.xml"}"
        println("$file : ${File(file).exists()}")

    }
}