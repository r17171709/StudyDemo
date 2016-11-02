package com.clevo.androidminademo.utils;

import android.util.Log;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**
 * Created by Clevo on 2016/11/1.
 */

public class ClientKeepAliveMessageTimeoutFactoryImp implements KeepAliveRequestTimeoutHandler {
    @Override
    public void keepAliveRequestTimedOut(KeepAliveFilter keepAliveFilter, IoSession ioSession) throws Exception {
        Log.d("ClientKeepAliveMessageT", "心跳超时");
    }
}
