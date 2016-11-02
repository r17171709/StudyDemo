package com.clevo.androidminademo.utils;

import android.util.Log;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Clevo on 2016/11/1.
 */

public class ClientSessionHandler extends IoHandlerAdapter {
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        Log.d("ClientSessionHandler", "服务器与客户端创建连接...");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        Log.d("ClientSessionHandler", "服务器与客户端连接打开...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        Log.d("ClientSessionHandler", "服务器与客户端断开连接...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        Log.d("ClientSessionHandler", "服务器发送异常...");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        Log.d("ClientSessionHandler", "客户端接受消息成功..."+message.toString());
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        Log.d("ClientSessionHandler", "客户端发送消息成功..."+message.toString());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        Log.d("ClientSessionHandler", "客户端进入空闲状态...");
    }
}
