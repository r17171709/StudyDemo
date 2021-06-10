package davenkin

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class RouteMappingByCodeBuilder implements Opcodes {
    // 生成文件的类名
    public static final String CLASS_NAME = "com/renyu/gradledemo/mapping/RouteMapping"

    static byte[] get(ArrayList<String> allMappingNames) {
        // 创建类
        // 创建构造方法（必须创建）
        // 创建get方法
        //   创建map
        //   塞入映射表内容
        //   返回map

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, CLASS_NAME, null, "java/lang/Object", null)
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
        methodVisitor.visitCode()
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitMaxs(1, 1)
        methodVisitor.visitEnd()

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "get", "()Ljava/util/Map;", "()Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", null)
        methodVisitor.visitCode()
        methodVisitor.visitTypeInsn(NEW, "java/util/HashMap")
        methodVisitor.visitInsn(DUP)
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false)
        methodVisitor.visitVarInsn(ASTORE, 0)
        allMappingNames.each {
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/renyu/gradledemo/mapping/" + it, "get", "()Ljava/util/Map;", false)
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "putAll", "(Ljava/util/Map;)V", true)
        }
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitInsn(ARETURN)
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd()

        return classWriter.toByteArray()
    }
}