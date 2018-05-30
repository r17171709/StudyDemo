package com.renyu.aocdemo.model;

public class UserModel {

    String name = "";

    public UserModel() {
        System.out.println("UserModel");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
