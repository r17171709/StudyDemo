package com.example.annotation;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by RG on 2016/6/2.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.example.annotation.ViewInject")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ViewInjectProcessor extends AbstractProcessor {
    private Messager messager;

    private Elements elementsUtils;

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();

        elementsUtils = processingEnv.getElementUtils();

        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ViewInject.class);
        if (!elements.isEmpty()) {
            HashMap<String, List<VariableElement>> maps = new HashMap<>();

            for (Element element : elements) {
                if (element.getKind() == ElementKind.FIELD) {
                    VariableElement variableElement = (VariableElement) element;
                    // 获取包名
                    String packageName = elementsUtils.getPackageOf(variableElement).getQualifiedName().toString();
                    String className = variableElement.getEnclosingElement().getSimpleName().toString();
                    String finalName = packageName + "." + className;
                    messager.printMessage(Diagnostic.Kind.NOTE, "finalName：" + finalName);
                    List<VariableElement> tmp = maps.containsKey(finalName) ? maps.get(finalName) : new ArrayList<>();
                    tmp.add(variableElement);
                    maps.put(finalName, tmp);
                }
            }

            if (maps.size() > 0) {
                Iterator<Map.Entry<String, List<VariableElement>>> it = maps.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, List<VariableElement>> entry = it.next();
                    List<VariableElement> variableElements = entry.getValue();
                    VariableElement variableElement = variableElements.get(0);
                    String packageName = elementsUtils.getPackageOf(variableElement).getQualifiedName().toString();
                    String oldClassName = variableElement.getEnclosingElement().getSimpleName().toString();
                    String className = variableElement.getEnclosingElement().getSimpleName().toString() + "$ViewBinding";

                    try {
                        JavaFileObject javaFileObject = filer.createSourceFile(className);
                        Writer writer = javaFileObject.openWriter();
                        writer.write("package " + packageName + ";");
                        writer.write("\n");
                        writer.write("import " + entry.getKey() + ";");
                        writer.write("\n");
                        writer.write("public class " + className + " {");
                        writer.write("\n");
                        writer.write("public void bind(" + oldClassName + " activity) {");
                        writer.write("\n");
                        for (VariableElement element : variableElements) {
                            ViewInject viewInject = element.getAnnotation(ViewInject.class);
                            // 获取ID
                            int id = viewInject.value();
                            // 获取变量名
                            String fieldName = element.getSimpleName().toString();
                            // 获取变量类型
                            TypeMirror typeMirror = element.asType();
                            writer.write("activity." + fieldName + " = activity.findViewById(" + id + ");");
                            writer.write("\n");
                        }
                        writer.write("}");
                        writer.write("\n");
                        writer.write("}");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
