package com.renyu.plugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class HelloClassVisitor(classWriter: ClassWriter) : ClassVisitor(Opcodes.ASM7, classWriter) {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return HelloMethodVisitor(api, methodVisitor, access, name!!, descriptor!!)
    }

    class HelloMethodVisitor(
        api: Int,
        methodVisitor: MethodVisitor,
        access: Int,
        name: String,
        descriptor: String
    ) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
        // 方法进入
        override fun onMethodEnter() {
            super.onMethodEnter()
            // 在所有方法前面插入Hello World!
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
            mv.visitLdcInsn("Hello World!")
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
        }
    }
}