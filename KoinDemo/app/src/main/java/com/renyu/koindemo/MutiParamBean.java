package com.renyu.koindemo;

import android.app.Application;

/**
 * Created by Administrator on 2020/9/4.
 */
public class MutiParamBean {
    public MutiParamBean(String param1) {
        this.param1 = param1;
    }

    public MutiParamBean(String param1, String param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public MutiParamBean(Application application) {
        this.application = application;
    }

    public MutiParamBean(BussinessServiceBean bussinessServiceBean) {
        this.bussinessServiceBean = bussinessServiceBean;
    }

    private String param1 = "p1";
    private String param2 = "p2";
    private Application application;
    private BussinessServiceBean bussinessServiceBean;

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public Application getApplication() {
        return application;
    }

    public BussinessServiceBean getBussinessServiceBean() {
        return bussinessServiceBean;
    }
}
