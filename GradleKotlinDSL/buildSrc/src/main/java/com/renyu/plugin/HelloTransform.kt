package com.renyu.plugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class HelloTransform : BaseTransform() {
    override fun getClassVisitor(classWriter: ClassWriter): ClassVisitor = HelloClassVisitor(classWriter)

    override fun getName() = "HelloTransform"
}