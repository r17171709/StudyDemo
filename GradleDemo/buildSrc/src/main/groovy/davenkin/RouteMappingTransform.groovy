package davenkin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RouteMappingTransform extends Transform {

    /**
     * 返回当前Transform名称
     * @return
     */
    @Override
    String getName() {
        return "RouteMappingTransform"
    }

    /**
     * 当前Transform需要输入的类型，这里是class类型
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 当前Transform需要收集的范围
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        // 整个工程范围
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否支持增量，通常返回false
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 所有的class收集好之后，会被打包传入此方法
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        // 1. 遍历所有的input
        // 2. 对input进行二次处理 （此环节不是必须的）
        // 3. 对input拷贝到目标目录

        RouteMappingCollect collect = new RouteMappingCollect()

        // 所有的输入类型拷贝到目标目录中
        transformInvocation.inputs.each { TransformInput transformInput ->
            // 文件夹类型
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                def destDir = transformInvocation.outputProvider.getContentLocation(
                        directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY
                )
                collect.collect(directoryInput.file)
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }
            // jar包类型
            transformInput.jarInputs.each { JarInput jarInput ->
                def destPath = transformInvocation.outputProvider.getContentLocation(
                        jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
                )
                collect.collectFromJarFile(jarInput.file)
                FileUtils.copyFile(jarInput.file, destPath)
            }
        }

        println("RouteMappingCollect >>> " + collect.getAll())

        // 将生成的字节码写入本地文件
        File mappingJarFile = transformInvocation.getOutputProvider().getContentLocation("route_mapping", getInputTypes(), getScopes(), Format.JAR)
        println("${getName()}   mappingJarFile = ${mappingJarFile.path} ")
        if (!mappingJarFile.getParentFile().exists()) {
            mappingJarFile.getParentFile().mkdirs()
        }
        if (mappingJarFile.exists()) {
            mappingJarFile.delete()
        }
        FileOutputStream fos = new FileOutputStream(mappingJarFile)
        JarOutputStream jarOutputStream = new JarOutputStream(fos)
        ZipEntry zipEntry = new ZipEntry(RouteMappingByCodeBuilder.CLASS_NAME + ".class")
        jarOutputStream.putNextEntry(zipEntry)
        jarOutputStream.write(RouteMappingByCodeBuilder.get(collect.getAll()))
        jarOutputStream.close()
        fos.close()
    }
}