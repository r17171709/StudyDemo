package com.rg.annotationdemo.annotation;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class EventHandler implements InvocationHandler {
    Context context;
    Method method;

    public EventHandler(Context context, Method method) {
        this.context = context;
        this.method = method;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.method.invoke(context, args);
    }
}
