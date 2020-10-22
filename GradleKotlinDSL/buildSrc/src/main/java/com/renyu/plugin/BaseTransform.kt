package com.renyu.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

abstract class BaseTransform : Transform() {
    // 并发编译
    private val mWaitableExecutor by lazy {
        WaitableExecutor.useGlobalSharedThreadPool()
    }

    // 需要处理的数据类型,用于确定我们需要对哪些类型的结果进行转换
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS

    // 表示Transform要操作的内容范围
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
        TransformManager.SCOPE_FULL_PROJECT

    // 是否支持增量编译
    override fun isIncremental() = true

    // 进行具体转换逻辑
    // 在transform方法中，我们需要将每个jar包和class文件复制到dest路径，这个dest路径就是下一个Transform的输入数据
    // 而在复制时，就可以将jar包和class文件的字节码做一些修改，再进行复制
    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)

        val outputProvider = transformInvocation.outputProvider
        // 当前是否是增量编译,由isIncremental方法决定的
        // 当上面的isIncremental()写的返回true,这里得到的值不一定是true,还得看当时环境.比如clean之后第一次运行肯定就不是增量编译嘛
        val isIncremental = transformInvocation.isIncremental
        if (!isIncremental) {
            // 不是增量编译则删除之前的所有文件
            outputProvider.deleteAll()
        }
        // 输出类型是Collection<TransformInput>,可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        transformInvocation.inputs.forEach {
            // 处理jar
            it.jarInputs.forEach {
                mWaitableExecutor.execute {
                    processJarInput(it, outputProvider, isIncremental)
                }
            }
            // 处理源码文件
            it.directoryInputs.forEach {
                mWaitableExecutor.execute {
                    processDirectoryInput(it, outputProvider, isIncremental)
                }
            }
        }
        // 等待所有任务结束
        mWaitableExecutor.waitForTasksWithQuickFail<Any>(true)
    }

    private fun processJarInput(
        jarInput: JarInput,
        outputProvider: TransformOutputProvider,
        isIncremental: Boolean
    ) {
        val status = jarInput.status
        val dest = outputProvider.getContentLocation(
            jarInput.file.absolutePath,
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )
        // 将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        if (isIncremental) {
            when (status) {
                Status.NOTCHANGED -> {
                }
                Status.ADDED, Status.CHANGED -> transformJar(jarInput.file, dest)
                Status.REMOVED -> {
                    if (dest.exists()) {
                        FileUtils.forceDelete(dest)
                    }
                }
            }
        } else {
            transformJar(jarInput.file, dest)
        }
    }

    private fun transformJar(jarInput: File, dest: File) {
        FileUtils.copyFile(jarInput, dest)
    }

    private fun processDirectoryInput(
        directoryInput: DirectoryInput,
        outputProvider: TransformOutputProvider,
        isIncremental: Boolean
    ) {
        val dest = outputProvider.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes,
            Format.DIRECTORY
        )
        FileUtils.forceMkdir(dest)
        // 将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        if (isIncremental) {
            val srcDirPath = directoryInput.file.absolutePath
            val destDirPath = dest.absolutePath
            val fileStatusMap = directoryInput.changedFiles
            fileStatusMap.forEach { inputFile, status ->
                val destFilePath = inputFile.absolutePath.replace(srcDirPath, destDirPath)
                val destFile = File(destFilePath)
                when (status) {
                    Status.NOTCHANGED -> {
                    }
                    Status.ADDED, Status.CHANGED -> {
                        FileUtils.touch(destFile)
                        println("拷贝单个文件")
                        traceFile(inputFile, destFile)
                    }
                    Status.REMOVED -> {
                        if (destFile.exists()) {
                            FileUtils.forceDelete(destFile)
                        }
                    }
                }
            }
        } else {
            transformDirectory(directoryInput, dest)
//            FileUtils.copyDirectory(directoryInput.file, dest)
        }
    }

    private fun transformDirectory(directoryInput: DirectoryInput, dest: File) {
        val extensions = arrayOf("class")
        // 递归地去获取该文件夹下面所有的文件
        val fileList = FileUtils.listFiles(directoryInput.file, extensions, true)
        val outputFilePath = dest.absolutePath
        val inputFilePath = directoryInput.file.absolutePath
        fileList.forEach {
            val outputFullPath = it.absolutePath.replace(inputFilePath, outputFilePath)
            val outputFile = File(outputFullPath)
            FileUtils.touch(outputFile)
            traceFile(it, outputFile)
        }
    }

    private fun traceFile(inputFile: File, outputFile: File) {
        if (isNeedTraceClass(inputFile)) {
            println("${inputFile.name} ---- 需要插桩 ----")
            val inputStream = FileInputStream(inputFile)
            val outputStream = FileOutputStream(outputFile)

            // 构建ClassReader对象
            val classReader = ClassReader(inputStream)
            // 构建ClassVisitor的实现类ClassWriter
            val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
            // 将ClassReader读取到的内容回调给ClassVisitor接口
            classReader.accept(getClassVisitor(classWriter), ClassReader.EXPAND_FRAMES)
            // 通过classWriter对象的toByteArray方法拿到完整的字节流
            outputStream.write(classWriter.toByteArray())
            inputStream.close()
            outputStream.close()
            println("${inputFile.name} ---- 插桩结束 ----")
        } else {
            FileUtils.copyFile(inputFile, outputFile)
        }
    }

    private fun isNeedTraceClass(file: File): Boolean {
        val name = file.name
        if (!name.endsWith(".class")
            || name.startsWith("R.class")
            || name.startsWith("R\$")
        ) {
            return false
        }
        return true
    }


    // 获取一个用于插桩的ClassVisitor
    abstract fun getClassVisitor(classWriter: ClassWriter): ClassVisitor
}