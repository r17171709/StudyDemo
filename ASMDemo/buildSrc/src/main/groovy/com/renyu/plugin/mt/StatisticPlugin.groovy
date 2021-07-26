package com.renyu.plugin.mt

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 在原本的编译命令后加-Dorg.gradle.debug=true --no-daemon,
 * -Dorg.gradle.debug=true表示gradle进程启动后等待调试器attach上才开始运行，--no-daemon表示是否开启daemon进程，不加这个参数默认是会开启daemon的。
 * 比如原本的编译命令是 ./gradlew clean :app:assembleDebug，需要调试的话就变为 ./gradlew clean :app:assembleDebug -Dorg.gradle.debug=true --no-daemon
 */
class StatisticPlugin implements Plugin<Project> {
    public static List<String> METHOD_TIMER_LIST

    @Override
    void apply(Project project) {
        println("this is entry ${project.name}")

        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new MethodTimerTransform())
        def statisticExtension = project.extensions.create("statistic", StatisticExtension)
        project.afterEvaluate {
            def methodTime = statisticExtension.getMethodTime()
            if (methodTime != null && methodTime.size() > 0) {
                METHOD_TIMER_LIST = new ArrayList<>()
                METHOD_TIMER_LIST.addAll(methodTime)
            }
        }
    }
}