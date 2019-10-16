package com.renyu.livedataretrofit;

import java.io.Serializable;

public class CityResponse implements Serializable {


    /**
     * city_name : 安庆
     * city_py : aq
     */

    private String city_name;
    private String city_py;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_py() {
        return city_py;
    }

    public void setCity_py(String city_py) {
        this.city_py = city_py;
    }
}
