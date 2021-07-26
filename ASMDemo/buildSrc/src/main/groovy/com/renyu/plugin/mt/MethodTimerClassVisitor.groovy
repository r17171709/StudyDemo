package com.renyu.plugin.mt

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class MethodTimerClassVisitor extends ClassVisitor {
    String methodOwner

    MethodTimerClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        methodOwner = name
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if ((access & Opcodes.ACC_INTERFACE) == 0 && name != "<init>" && name != "<clinit>") {
            methodVisitor = new MethodTimerAdviceAdapter(api, methodVisitor, methodOwner, access, name, descriptor)
        }
        return methodVisitor
    }
}