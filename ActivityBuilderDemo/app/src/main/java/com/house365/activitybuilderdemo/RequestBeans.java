package com.house365.activitybuilderdemo;

import java.io.Serializable;

public class RequestBeans implements Serializable {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public RequestBeans(String userName) {
        this.userName = userName;
    }
}
