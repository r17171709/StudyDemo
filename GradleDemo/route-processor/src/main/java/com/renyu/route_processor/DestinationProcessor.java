package com.renyu.route_processor;

import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.renyu.route.annotations.Destination;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Processor对象不要倒错包
 * SupportedAnnotationTypes、SupportedSourceVersion要加入
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.renyu.route.annotations.Destination")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DestinationProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();

        filer = processingEnv.getFiler();
    }

    /**
     * 编译期找到注解后回调
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 避免多次重复调用
        System.out.println("DestinationProcessor  >>>  process roundEnv.processingOver(): " + roundEnv.processingOver() + "\n");
        if (roundEnv.processingOver()) {
            return false;
        }

        System.out.println("DestinationProcessor  >>>  process start");

        // 获取所有标记了 @Destination 注解类的信息
        Set<Element> allDestinationElements = (Set<Element>) roundEnv.getElementsAnnotatedWith(Destination.class);

        System.out.println("DestinationProcessor  >>>  all elements: " + allDestinationElements.size() + "\n");

        // 没有设置注解，跳过
        if (allDestinationElements.size() < 1) {
            return false;
        }

        JsonArray jsonArray = new JsonArray();

        String className = "RouteMapping_" + System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("package com.renyu.gradledemo.mapping;\n");
        builder.append("\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n");
        builder.append("\n");
        builder.append("public class " + className + " {\n");
        builder.append("    public static Map<String, String> get() {\n");
        builder.append("        Map<String, String> mapping = new HashMap<>();\n");

        for (Element element : allDestinationElements) {
            Destination destination = element.getAnnotation(Destination.class);
            if (destination != null) {
                String url = destination.url();
                String description = destination.description();
                String realPath = ((TypeElement) element).getQualifiedName().toString();

                builder.append("        mapping.put(\"" + url + "\", \"" + realPath + "\");\n");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("url", url);
                jsonObject.addProperty("description", description);
                jsonObject.addProperty("realPath", realPath);
                jsonArray.add(jsonObject);

                System.out.println("DestinationProcessor  >>>  url:" + url + " description:" + description + " realPath:" + realPath + "\n");
            }
        }

        builder.append("        return mapping;\n");
        builder.append("    }\n");
        builder.append("}");

        String mappingFullClassName = "com.renyu.gradledemo.mapping." + className;

        try {
            // 写入自动生成的类到本地文件中
            JavaFileObject javaFileObject = filer.createSourceFile(mappingFullClassName);
            Writer writer = javaFileObject.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
            // 由于是kotlin，故生成的文件在相应模块下的 /build/generated/source/kapt/debug 中
            System.out.println("DestinationProcessor  >>>  文件生成成功\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println(builder.toString());

        System.out.println("DestinationProcessor  >>>  process end\n");

        // 获取kapt定义的参数
        String root_project_dir = processingEnv.getOptions().get("root_project_dir");
        System.out.println("DestinationProcessor  >>>  root_project_dir: " + root_project_dir + "\n");
        if (root_project_dir == null) {
            return true;
        }
        File mappingFile = new File(root_project_dir, "mapping_" + System.currentTimeMillis() + ".json");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(mappingFile));
            writer.write(jsonArray.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 当前处理器支持的注解类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Destination.class.getCanonicalName());
    }
}
