package davenkin

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import davenkin.groovydemo.LSN1
import davenkin.groovydemo.LSN2
import org.gradle.api.Plugin
import org.gradle.api.Project

class DateAndTimePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 注册Transfor的过程
        if (project.plugins.hasPlugin(AppPlugin)) {
            AppExtension appExtension = project.extensions.getByType(AppExtension)
            Transform transform = new RouteMappingTransform()
            appExtension.registerTransform(transform)
        }

        // 1. 此处将自定义参数传递到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.getRootProject().projectDir.absolutePath)
            }
        }
        // 2. 删除之前构建的产物
        project.clean.doFirst {
            File file = project.rootProject.projectDir
            file.listFiles().each {
                if (it.name.startsWith("mapping_1")) {
                    it.delete()
                }
            }
        }

        // 每个Gradle的Project都维护了一个ExtenionContainer,我们可以通过project.extentions进行访问，比如读取额外的Property和定义额外的Property等
        project.extensions.create("dateAndTime", DateAndTimePluginExtension)
        project.extensions.create("RouteExtension", RouteExtension)
        project.task("showTime") {
            doLast {
                println("${new Date().format(project.dateAndTime.timeFormat)}")
            }
        }
        project.tasks.create("showDate") {
            doLast {
                println("${new Date().format(project.dateAndTime.dateFormat)}")
            }
        }
        project.tasks.create("lsn1") {
            doLast {
                LSN1 lsn1 = new LSN1()
                lsn1.objectDemo()
            }
        }
        project.tasks.create("lsn2") {
            doLast {
                LSN2 lsn2 = new LSN2()
                lsn2.fileDemo()
            }
        }

        project.afterEvaluate {
            DateAndTimePluginExtension extension = project["dateAndTime"]
            println "extension.timeFormat值为：${extension.timeFormat}"

            RouteExtension routeExtension = project["RouteExtension"]
            println "routeExtension.wikiDir值为：${routeExtension.wikiDir}"

            // 在javac（compileDebugJavaWithJavac）之后，汇总生成文档
            project.tasks.findAll {
                it.name.startsWith("compile") && it.name.endsWith("JavaWithJavac")
            }.each {
                it.doLast {
                    project.rootProject.projectDir.listFiles().each {
                        if (it.name.startsWith("mapping_")) {
                            it.delete()
                        }
                    }
                    // 后续直接生成文档
                }
            }
        }
    }
}