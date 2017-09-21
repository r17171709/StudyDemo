package com.renyu.kotlin.chapter9;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@MyKAnnotation(name = "Hello", age = 20)
public class AnnotationClass {

    @MyKAnnotation(name = "Hello AnnotationClass", age = 20)
    public AnnotationClass(String value) {

    }

    @MyKAnnotation(name = "WORLD", age = 30)
    String value="value";

    @MyKAnnotation(age = 10)
    public void function() {}

    public static void main(String[] args) {
        try {
            Class class_ = Class.forName("com.renyu.kotlin.chapter9.AnnotationClass");
            for (Constructor constructor : class_.getConstructors()) {
                if (constructor.getAnnotation(MyKAnnotation.class) != null) {
                    MyKAnnotation annotation_con = (MyKAnnotation) constructor.getAnnotation(MyKAnnotation.class);
                    System.out.println(annotation_con.name()+" "+annotation_con.age());
                }
            }
            MyKAnnotation annotation = (MyKAnnotation) class_.getAnnotation(MyKAnnotation.class);
            System.out.println(annotation.name()+" "+annotation.age());
            Field field=class_.getDeclaredField("value");
            MyKAnnotation annotation_field = field.getAnnotation(MyKAnnotation.class);
            System.out.println(annotation_field.name()+" "+annotation_field.age());
            for (Method method : class_.getMethods()) {
                if (method.getAnnotation(MyKAnnotation.class) != null) {
                    MyKAnnotation annotation_method = method.getAnnotation(MyKAnnotation.class);
                    System.out.println(annotation_method.name()+" "+annotation_method.age());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
