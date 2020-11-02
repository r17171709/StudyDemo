package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * Created by RG on 2016/6/1.
 * <p>
 * AutoService注解无法生成META-INF文件   https://blog.csdn.net/cpcpcp123/article/details/103871815
 * Android Studio 调试 AnnotationProcessor - 傻瓜版   https://blog.csdn.net/stupid56862/article/details/82224957
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.example.PrintInject")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PrintInjectProcessor extends AbstractProcessor {
    // 日志打印
    private Messager messager;

    // 文件处理
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();

        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(PrintInject.class);

        ArrayList<Integer> results = new ArrayList<>();

        if (!elements.isEmpty()) {
            handleDestination(elements, PrintInject.class, results);
        }

        if (results.size() > 0) {
            try {
                // java文件生成class文件的位置，/app/build/intermediates/javac/debug/classes/目录下
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "com.rg.annotationdemo", "Demo.java");
                String resourcePath = resource.toUri().getPath();
                messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath：" + resourcePath);

                TypeSpec.Builder classBuilder = TypeSpec.classBuilder("Demo").addModifiers(Modifier.PUBLIC);
                MethodSpec methodSpec = MethodSpec.methodBuilder("show").addModifiers(Modifier.PUBLIC)
                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                        .build();
                classBuilder.addMethod(methodSpec);
                for (int i = 0; i < results.size(); i++) {
                    FieldSpec fieldSpec = FieldSpec.builder(int.class, "i" + i).addModifiers(Modifier.PUBLIC).initializer("$L", Integer.parseInt(results.get(i).toString())).build();
                    classBuilder.addField(fieldSpec).build();
                }
                TypeSpec typeSpec = classBuilder.build();
                JavaFile javaFile = JavaFile.builder("com.rg.annotationdemo", typeSpec).build();
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void handleDestination(Set<? extends Element> elements, Class<? extends Annotation> annotationClazz, ArrayList<Integer> results) {
        for (Element element : elements) {
            // 类
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                // 全类名
                typeElement.getQualifiedName().toString();
                // 类名
                typeElement.getSimpleName().toString();
                // 包名
                ((PackageElement) (typeElement.getEnclosingElement())).getQualifiedName().toString();
                Annotation annotation = element.getAnnotation(annotationClazz);
                if (annotation instanceof PrintInject) {
                    PrintInject printInject = (PrintInject) annotation;
                    for (int i : printInject.value()) {
                        results.add(i);
                        messager.printMessage(Diagnostic.Kind.NOTE, "打印的值为：" + i);
                    }
                }
            } else if (element.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;
            } else if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement executableElement = (ExecutableElement) element;
            } else if (element.getKind() == ElementKind.PARAMETER) {
            }
        }
    }
}
