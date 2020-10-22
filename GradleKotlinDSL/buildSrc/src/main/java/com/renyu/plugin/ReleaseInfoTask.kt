package com.renyu.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class ReleaseInfoTask : DefaultTask() {
    @TaskAction
    fun doAction() {
        println("ReleaseInfoTask")
    }
}