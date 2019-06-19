package com.house365.builderlibrary.builder;

import com.house365.builderlibrary.entity.Field;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ActivityBuilder extends BaseBuilder {
    public ActivityBuilder(TypeElement element) {
        super(element);
    }

    @Override
    void buildInternal(TypeSpec.Builder classBuilder) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("fillIntent")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ClassName.get("android.content", "Intent"), "intent");
        for (Field field : fields) {
            builder.addStatement("intent.putExtra($S, $L)", field.getName(), field.getName());
        }
        classBuilder.addMethod(builder.build());

        MethodSpec.Builder builderStart = MethodSpec.methodBuilder("start")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("android.content", "Context"), "context")
                .addStatement("Intent intent = new Intent(context, "+element.getSimpleName()+".class)")
                .addStatement("fillIntent(intent)")
                .addStatement("context.startActivity(intent)");
        classBuilder.addMethod(builderStart.build());
    }
}
