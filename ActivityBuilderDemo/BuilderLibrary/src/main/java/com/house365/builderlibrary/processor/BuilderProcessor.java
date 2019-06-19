package com.house365.builderlibrary.processor;

import com.house365.annotation.annotation.Builder;
import com.house365.annotation.annotation.BuilderActivity;
import com.house365.annotation.annotation.BuilderField;
import com.house365.builderlibrary.builder.ActivityBuilder;
import com.house365.builderlibrary.builder.BaseBuilder;
import com.house365.builderlibrary.utils.ProcessorEnv;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.*;

public class BuilderProcessor extends AbstractProcessor {
    private HashMap<Element, BaseBuilder> classMap;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> supportedAnnotation : getSupportedAnnotations()) {
            types.add(supportedAnnotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(Builder.class);
        annotations.add(BuilderActivity.class);
        return annotations;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        ProcessorEnv.getInstance().init(processingEnvironment);
        classMap = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BuilderActivity.class)) {
            if (element.getKind().isClass()) {
                messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName() + " Activity");
                classMap.put(element, (new ActivityBuilder((TypeElement) element)));
            }
        }

        for (Element element : roundEnvironment.getElementsAnnotatedWith(BuilderField.class)) {
            if (element.getKind().isField() && classMap.get(element.getEnclosingElement()) != null) {
                classMap.get(element.getEnclosingElement()).addFiled(element);
            }
        }
        build();
        return true;
    }

    private void build() {
        Iterator<Map.Entry<Element, BaseBuilder>> iterator = classMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Element, BaseBuilder> entry = iterator.next();
            entry.getValue().build();
        }
    }
}
