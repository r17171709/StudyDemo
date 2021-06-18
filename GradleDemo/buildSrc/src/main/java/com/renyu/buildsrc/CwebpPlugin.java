package com.renyu.buildsrc;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.api.ApplicationVariant;

import org.antlr.v4.misc.Utils;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskInputs;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class CwebpPlugin implements Plugin<Project> {
    // android的配置
    private AppExtension appExtension;
    // 自定义配置
    private WebpConfig webpConfig;

    private Logger logger;

    private ArrayList<File> imageList = new ArrayList<>();

    @Override
    public void apply(Project project) {
        if (!project.getPlugins().hasPlugin(AppPlugin.class)) {
            throw new GradleException("必须在android application插件中使用改插件");
        }

        appExtension = project.getExtensions().getByType(AppExtension.class);

        project.getExtensions().create("webpConfig", WebpConfig.class);

        logger = project.getLogger();

        printAllTask(project);
        init(project);
    }

    private void printAllTask(Project project) {
        project.getGradle().getTaskGraph().whenReady(new Action<TaskExecutionGraph>() {
            @Override
            public void execute(TaskExecutionGraph taskGraph) {
                for (Task task : taskGraph.getAllTasks()) {
//                    logger.log(LogLevel.ERROR, "printAllTask:  " + task.getName());
                }
            }
        });
    }

    private void init(Project project) {
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                webpConfig = project.getExtensions().findByType(WebpConfig.class);
                appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {
                        String name = applicationVariant.getName();
                        // debug模式下不允执行
//                        if (name.contains("debug") && !webpConfig.isDebugOn()) {
//                            return;
//                        }
                        convert(project, applicationVariant);
                    }
                });
            }
        });
    }

    private void convert(Project project, ApplicationVariant variant) {
        String variantName = Utils.capitalize(variant.getName());
        // 获取 mergeDebugResources/mergeReleaseResources 任务
        Task mergeResouceTask = project.getTasks().findByName("merge" + variantName + "Resources");
        if (mergeResouceTask != null) {
            mergeResouceTask.doFirst(new Action<Task>() {
                @Override
                public void execute(Task task) {
                    TaskInputs taskInputs = mergeResouceTask.getInputs();
                    Set<File> fileSet = taskInputs.getFiles().getFiles();
                    for (File file : fileSet) {
                        // 遍历文件夹获取所有图片
                        getAllImages(file);
                    }
                    startConvertAndCompress();
                }
            });
        }
    }

    private void getAllImages(File file) {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                if (listFile.isDirectory()) {
                    getAllImages(listFile);
                } else {
                    filterImage(file);
                }
            }
        } else {
            filterImage(file);
        }
    }

    private void filterImage(File file) {
        imageList.add(file);
    }

    /**
     * 开始转换
     */
    private void startConvertAndCompress() {
        logger.log(LogLevel.ERROR, "startConvertAndCompress      "+imageList.size());
    }
}
