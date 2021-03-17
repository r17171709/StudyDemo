package com.rg.annotationdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseEvent {
    // 方法名  如setOnClickListener
    String funcName();
    // 事件对象  如View.OnClickListener
    Class<?> listenerType();
    // 执行事件对象中的方法名  如onClick(View v)
    String callBackMethod();
}
