package com.renyu.architecturedemo.util;

/**
 * Created by renyu on 2017/2/20.
 */

public class NetworkException extends Exception {
    private int result;
    private String message;

    public int getResult() {
        return result;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public NetworkException(int result, String message1) {
        this.result = result;
        this.message = message1;
    }
}
