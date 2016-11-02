package com.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * Created by Clevo on 2016/11/1.
 */

public class ServerKeepAliveMessageFactoryImp implements KeepAliveMessageFactory {
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
        return null;
    }

    @Override
    public Object getResponse(IoSession ioSession, Object o) {
        System.out.println("回发心跳...");
        return Params.RECEIVE;
    }
}
