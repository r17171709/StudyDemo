package com.rg.annotationdemo.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by RG on 2016/6/1.
 */
public class InjectorProcessor {

    public void process(final Object object) {
        Class class_=object.getClass();
        Method[] methods=class_.getDeclaredMethods();
        for (final Method method : methods) {
            onClick clickMethod=method.getAnnotation(onClick.class);
            if (clickMethod!=null) {
                if (object instanceof Activity) {
                    for (int id : clickMethod.value()) {
                        View view=((Activity) object).findViewById(id);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.invoke(object);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
