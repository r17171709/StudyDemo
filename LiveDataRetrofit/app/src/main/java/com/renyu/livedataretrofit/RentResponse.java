package com.renyu.livedataretrofit;

/**
 * Created by renyu on 2017/6/6.
 */

public class RentResponse<T> {
    private T data;
    private int result;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
