package com.house365.builderlibrary.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class Field {
    private VariableElement element;

    public Field(VariableElement element) {
        this.element = element;
    }

    public String getName() {
        return element.getSimpleName().toString();
    }

    public TypeName getAsTypeName() {
        return ClassName.get(element.asType());
    }
}
