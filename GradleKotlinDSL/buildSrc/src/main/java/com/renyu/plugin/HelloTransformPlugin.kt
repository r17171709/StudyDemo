package com.renyu.plugin

import org.gradle.api.Project

class HelloTransformPlugin : BaseTransformPlugin() {
    override fun getCustomTransform(project: Project) = HelloTransform()
}