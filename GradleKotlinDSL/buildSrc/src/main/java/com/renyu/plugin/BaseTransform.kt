package com.renyu.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor
import org.apache.commons.io.FileUtils
import java.io.File

abstract class BaseTransform : Transform() {
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
        //等待所有任务结束
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
                Status.NOTCHANGED -> {}
                Status.ADDED, Status.CHANGED -> FileUtils.copyFile(jarInput.file, dest)
                Status.REMOVED -> {
                    if (dest.exists()) {
                        FileUtils.forceDelete(dest)
                    }
                }
            }
        } else {
            FileUtils.copyFile(jarInput.file, dest)
        }
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
                    Status.NOTCHANGED -> {}
                    Status.ADDED, Status.CHANGED -> {
                        FileUtils.touch(destFile)
                        println("拷贝单个文件")
                        FileUtils.copyFile(inputFile, destFile)
                    }
                    Status.REMOVED -> {
                        if (destFile.exists()) {
                            FileUtils.forceDelete(destFile)
                        }
                    }
                }
            }
        } else {
            FileUtils.copyDirectory(directoryInput.file, dest)
        }
    }
}