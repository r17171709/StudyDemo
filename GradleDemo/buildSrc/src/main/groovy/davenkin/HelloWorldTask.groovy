package davenkin


import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

class HelloWorldTask extends DefaultTask {
    @Option
    String message = "default helloTask"

    // 执行在doLast和doFirst之间
    @TaskAction
    def helloMessage() {
        println "helloMessage: ${message}"
    }
}