package com.house365.builderlibrary.utils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ProcessorEnv {
    private Types types;
    private Elements elements;
    private Messager messager;
    private Filer filer;

    private static volatile ProcessorEnv processorEnv;

    public static ProcessorEnv getInstance() {
        if (processorEnv == null) {
            synchronized (ProcessorEnv.class) {
                if (processorEnv == null) {
                    processorEnv = new ProcessorEnv();
                }
            }
        }
        return processorEnv;
    }

    public void init(ProcessingEnvironment processingEnvironment) {
        this.types = processingEnvironment.getTypeUtils();
        this.elements = processingEnvironment.getElementUtils();
        this.messager = processingEnvironment.getMessager();
        this.filer = processingEnvironment.getFiler();
    }

    public Types getTypes() {
        return types;
    }

    public Elements getElements() {
        return elements;
    }

    public Messager getMessager() {
        return messager;
    }

    public Filer getFiler() {
        return filer;
    }
}
