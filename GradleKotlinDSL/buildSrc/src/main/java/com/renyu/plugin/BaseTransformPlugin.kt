package com.renyu.plugin

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BaseTransformPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val appExtension = project.extensions.getByType(AppExtension::class.java)
        appExtension.registerTransform(getCustomTransform(project))
    }

    abstract fun getCustomTransform(project: Project): Transform
}