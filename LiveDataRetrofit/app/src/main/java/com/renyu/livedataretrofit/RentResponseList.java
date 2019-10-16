package com.renyu.livedataretrofit;

import java.util.List;

/**
 * Created by renyu on 2017/6/6.
 */

public class RentResponseList<T> {
    private List<T> data;
    private int result;
    private String msg;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
