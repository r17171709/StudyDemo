package com.renyu.kotlin.chapter9;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectStudy {
    public static void main(String[] args) {
        try {
            Class reflectStudyAClass = Class.forName("com.renyu.kotlin.chapter9.ReflectStudyA");
            ReflectStudyA reflectStudyA = (ReflectStudyA) reflectStudyAClass.getDeclaredConstructor().newInstance();
            Field aValueField = reflectStudyAClass.getDeclaredField("aValue");
            aValueField.setAccessible(true);
            String aValue = (String) aValueField.get(reflectStudyA);
            Method method = reflectStudyAClass.getDeclaredMethod("toIntValue", new Class[] {String.class});
            method.setAccessible(true);
            method.invoke(reflectStudyA, aValue);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}


