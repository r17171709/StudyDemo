package com.renyu.kotlin.chapter5;

import java.util.ArrayList;

public class GenericsStudyJava {
    public static void main(String[] args) {
//        GenericsStudyJava genericsStudyJava=new GenericsStudyJava();
//
//        // extends关键字声明了类型的上界，表示参数化的类型可能是所指定的类型，或者是此类型的子类
//        // 因此集合的类型是不确定的，编译器也不知道你到底传来的是什么。由于基类无法转换为子类，所以无法添加
//        // 相反，读取数据时不管实际的list是什么类型，但由于子类转换为基类，可以知道它至少会返回一个ClassParent类型
//        genericsStudyJava.addExtends(new ArrayList<ClassChild>());
//        genericsStudyJava.addExtends(new ArrayList<ClassParent>());
//        genericsStudyJava.addExtends(new ArrayList<ClassGrantParent>());
//        ArrayList arrayListTemp1=new ArrayList<ClassChild>();
//        arrayListTemp1.add(new ClassChild());
//        genericsStudyJava.getExtends(arrayListTemp1);
//
//        // super关键字声明了类型的下界，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至Object
//        // 集合类型至少是一个ClassParent类型，由于子类转换为基类，所以可以添加ClassParent类型及其子类
//        // 相反，取数据的时候因为不确定到底是哪种类型，所以需要自行判断
//        genericsStudyJava.addSuper(new ArrayList<ClassChild>());
//        genericsStudyJava.addSuper(new ArrayList<ClassParent>());
//        genericsStudyJava.addSuper(new ArrayList<ClassGrantParent>());
//        ArrayList arrayListTemp2=new ArrayList<ClassGrantParent>();
//        arrayListTemp2.add(new ClassGrantParent());
//        genericsStudyJava.getSuper(arrayListTemp2);
//
//        ArrayList<Object> objectArrayList1=new ArrayList<>();
//        ArrayList<String> stringArrayList2=new ArrayList<>();
//        objectArrayList1=stringArrayList2;
//        ArrayList<? extends Object> objectArrayList2=new ArrayList<>();
//        objectArrayList2=stringArrayList2;
//
//        ArrayList<? super ClassParent> listClassParentTemp=new ArrayList<>();
//        ArrayList<ClassGrantParent> listClassGrantParentTemp=new ArrayList<>();
//        listClassParentTemp=listClassGrantParentTemp;
//
//        ArrayList<? extends ClassParent> listClassParentTemp2=new ArrayList<>();
//        ArrayList<ClassChild> listTemp2=new ArrayList<>();
//        listClassParentTemp2=listTemp2;
//
//        Impl<? extends Object> objectImpl=new Impl<String>() {};

        ArrayList<? extends Parnet> parnets=new ArrayList<>();
        ArrayList<Child> children=new ArrayList<>();
        parnets=children;
    }
//
//    public void addExtends(ArrayList<? extends ClassParent> list) {
//        list.add(new ClassParent());
//        list.add(new ClassChild());
//        list.add(new ClassGrantParent());
//    }
//
//    public void getExtends(ArrayList<? extends ClassParent> list) {
//        list.get(0);
//    }
//
//    public void addSuper(ArrayList<? super ClassParent> list) {
//        list.add(new ClassParent());
//        list.add(new ClassChild());
//        list.add(new ClassGrantParent());
//    }
//
//    public void getSuper(ArrayList<? super ClassParent> list) {
//        list.get(0);
//    }
//
//    interface Impl<T> {}

    class Parnet {}
    class Child extends Parnet {}
}