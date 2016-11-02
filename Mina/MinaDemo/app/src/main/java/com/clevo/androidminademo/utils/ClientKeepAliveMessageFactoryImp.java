package com.clevo.androidminademo.utils;

import android.util.Log;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * Created by Clevo on 2016/11/1.
 */

public class ClientKeepAliveMessageFactoryImp implements KeepAliveMessageFactory {
    @Override
    public boolean isRequest(IoSession ioSession, Object o) {
        if (o instanceof String && o.equals(Params.SEND)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession ioSession, Object o) {
        if (o instanceof String && o.equals(Params.RECEIVE)) {
            return true;
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession ioSession) {
        Log.d("ClientKeepAliveMessageF", "发送心跳...");
        return Params.SEND;
    }

    @Override
    public Object getResponse(IoSession ioSession, Object o) {
        return null;
    }
}
