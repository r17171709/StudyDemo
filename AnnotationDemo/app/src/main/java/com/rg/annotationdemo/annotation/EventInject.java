package com.rg.annotationdemo.annotation;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class EventInject {
    public static void inject(Context context) {
        Class<?> clazz = context.getClass();
        Method[] contextMethods = clazz.getDeclaredMethods();
        for (int i = 0; i < contextMethods.length; i++) {
            Method method = contextMethods[i];
            for (int j = 0; j < method.getAnnotations().length; j++) {
                Annotation annotation = method.getAnnotations()[j];
                Class<?> annotationClass = annotation.annotationType();
                BaseEvent baseEvent = annotationClass.getAnnotation(BaseEvent.class);
                if (baseEvent != null) {
                    try {
                        Method valueMethod = annotationClass.getDeclaredMethod("value");
                        int viewId = (int) valueMethod.invoke(annotation);
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, viewId);

                        String funcName = baseEvent.funcName();
                        Class<?> listenerType = baseEvent.listenerType();
                        String callBackMethod = baseEvent.callBackMethod();

                        // 只能通过动态代理的方式来改变对象中已经实现好的方法
                        Object proxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, new EventHandler(context, method));

                        Method clickMethod = view.getClass().getMethod(funcName, listenerType);
                        clickMethod.invoke(view, proxyInstance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    continue;
                }
            }
        }
        String a = "";
    }
}
