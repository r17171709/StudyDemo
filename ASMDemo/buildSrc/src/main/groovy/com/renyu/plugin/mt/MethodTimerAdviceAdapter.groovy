package com.renyu.plugin.mt

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

class MethodTimerAdviceAdapter extends AdviceAdapter {
    String methodOwner
    String methodName
    int slotIndex

    MethodTimerAdviceAdapter(int api, MethodVisitor mv, String owner, int access, String name, String descriptor) {
        super(api, mv, access, name, descriptor)

        this.methodOwner = owner
        this.methodName = name
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        StatisticPlugin.METHOD_TIMER_LIST.forEach {
            if (methodOwner.contains(it)) {
                slotIndex = newLocal(Type.LONG_TYPE)
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LSTORE, slotIndex);
            }
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        StatisticPlugin.METHOD_TIMER_LIST.forEach {
            if (methodOwner.contains(it)) {
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LSTORE, slotIndex + 2);
                mv.visitVarInsn(LLOAD, slotIndex + 2);
                mv.visitVarInsn(LLOAD, slotIndex + 0);
                mv.visitInsn(LSUB);
                mv.visitVarInsn(LSTORE, slotIndex + 4);
                mv.visitVarInsn(LLOAD, slotIndex + 4);
                mv.visitLdcInsn(new Long(500L));
                mv.visitInsn(LCMP)
                Label label0 = new Label();
                mv.visitJumpInsn(IFLE, label0);
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("\u7a0b\u5e8f\u8fd0\u884c\u65f6\u95f4\uff1a ");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitVarInsn(LLOAD, slotIndex + 4);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                mv.visitLdcInsn("ms");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                mv.visitLabel(label0);
            }
        }
    }
}