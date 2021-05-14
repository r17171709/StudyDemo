package davenkin

import davenkin.groovydemo.LSN1
import davenkin.groovydemo.LSN2
import org.gradle.api.Plugin
import org.gradle.api.Project

class DateAndTimePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 每个Gradle的Project都维护了一个ExtenionContainer,我们可以通过project.extentions进行访问，比如读取额外的Property和定义额外的Property等
        project.extensions.create("dateAndTime", DateAndTimePluginExtension)
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
    }
}