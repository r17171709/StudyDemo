package com.house365.builderlibrary.builder;

import com.house365.builderlibrary.entity.Field;
import com.house365.builderlibrary.utils.ProcessorEnv;
import com.squareup.javapoet.*;

import javax.lang.model.element.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseBuilder {
    abstract void buildInternal(TypeSpec.Builder classBuilder);

    ArrayList<Field> fields = new ArrayList<>();

    TypeElement element;

    public BaseBuilder(TypeElement element) {
        this.element = element;
    }

    private String getClassName() {
        ArrayList<String> list = new ArrayList<>();
        list.add(element.getSimpleName().toString());
        Element encloseingElement = element.getEnclosingElement();
        while (encloseingElement != null && encloseingElement.getKind() != ElementKind.PACKAGE) {
            list.add(encloseingElement.getSimpleName().toString());
            encloseingElement = encloseingElement.getEnclosingElement();
        }
        Collections.reverse(list);
        // final class name
        String className = "";
        for (String s : list) {
            className += s + "_";
        }
        className += "Builder";
        return className;
    }

    private String getPackageName() {
        return ((PackageElement) (element.getEnclosingElement())).getQualifiedName().toString();
    }

    public void build() {
        boolean isAbstract = element.getModifiers().contains(Modifier.ABSTRACT);
        if (isAbstract) {
            return;
        }

        String className = getClassName();
        String packageName = getPackageName();

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        ClassName builderClassTypeName = ClassName.get(packageName, className);
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderClassTypeName)
                .addStatement("$T builder = new $T()", builderClassTypeName, builderClassTypeName)
                .addStatement("return builder");
        classBuilder.addMethod(methodBuilder.build());

        for (Field field : fields) {
            classBuilder.addField(FieldSpec.builder(field.getAsTypeName(), field.getName(), Modifier.PRIVATE).build());
            MethodSpec.Builder set = MethodSpec.methodBuilder(field.getName())
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(field.getAsTypeName(), field.getName())
                    .addStatement("this." + field.getName() + " = " + field.getName())
                    .addStatement("return this")
                    .returns(builderClassTypeName);
            classBuilder.addMethod(set.build());
        }

        buildInternal(classBuilder);

        writeJavaToFile(classBuilder.build());

    }

    private void writeJavaToFile(TypeSpec typeSpec) {
        String packageName = getPackageName();
        JavaFile file = JavaFile.builder(packageName, typeSpec).build();
        try {
            file.writeTo(ProcessorEnv.getInstance().getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFiled(Element element) {
        fields.add(new Field((VariableElement) element));

//        ProcessorEnv.getInstance().getMessager().printMessage(Diagnostic.Kind.NOTE, "build " + element.getSimpleName());
    }
}
