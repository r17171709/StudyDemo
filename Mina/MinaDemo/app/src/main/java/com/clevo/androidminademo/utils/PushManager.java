package com.clevo.androidminademo.utils;

import android.util.Log;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Clevo on 2016/11/1.
 */

public class PushManager {

    private static volatile PushManager manager;

    private static NioSocketConnector connector;
    private static ConnectFuture connectFuture;
    private static IoSession ioSession;

    private PushManager() {
        connector=new NioSocketConnector();
        connector.setConnectTimeoutMillis(Params.CONNECT_TIMEOUT);
        //为接收器设置管理服务
        connector.setHandler(new ClientSessionHandler());
        //设置过滤器（使用Mina提供的文本换行符编解码器）
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue())));
        //读写通道5秒内无操作进入空闲状态
        connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, Params.REQUEST_TIMEOUT);
        //设置读取数据的缓冲区大小
        connector.getSessionConfig().setReadBufferSize(2048);
        //设置心跳
        KeepAliveMessageFactory heartBeatFactory = new ClientKeepAliveMessageFactoryImp();
        KeepAliveRequestTimeoutHandler heartBeatHandler = new ClientKeepAliveMessageTimeoutFactoryImp();
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory, IdleStatus.BOTH_IDLE, heartBeatHandler);
        //是否回发
        heartBeat.setForwardEvent(true);
        //心跳发送频率
        heartBeat.setRequestInterval(Params.REQUEST_INTERVAL);
        connector.getSessionConfig().setKeepAlive(true);
        connector.getFilterChain().addLast("keepalive", heartBeat);
    }

    public static PushManager getInstance() {
        if (manager==null) {
            synchronized (PushManager.class) {
                manager=new PushManager();
            }
        }
        return manager;
    }

    /**
     * 连接
     * @return
     */
    public boolean connect() {
        if (connector!=null && connector.isActive() &&
                connectFuture!=null && connectFuture.isConnected() &&
                ioSession!=null && ioSession.isConnected()) {
            return true;
        }
        try {
            connectFuture=connector.connect(new InetSocketAddress(Params.HOSTNAME, Params.PORT));
            //等待是否连接成功，相当于是转异步执行为同步执行。
            connectFuture.awaitUninterruptibly();
            //连接成功后获取会话对象。如果没有上面的等待，由于connect()方法是异步的，session 可能会无法获取。
            ioSession=connectFuture.getSession();
            sendMessage("Hello Client");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("PushManager", "服务器与客户端连接异常...");
        }
        return false;
    }

    /**
     * 关闭
     */
    public void close() {
        if(ioSession!=null && ioSession.isConnected()){
            ioSession.close(false);
        }
        if(connectFuture!=null && connectFuture.isConnected()) {
            connectFuture.cancel();
        }
        if(connector!=null && !connector.isDisposed()) {
            connector.dispose();
        }
    }

    /**
     * 发送
     * @param message
     * @return
     */
    public boolean sendMessage(String message){
        if (ioSession==null || !ioSession.isConnected()){
            return false;
        }
        WriteFuture writeFuture=ioSession.write(message);
        if (writeFuture==null){
            return false;
        }
        writeFuture.awaitUninterruptibly();
        if (writeFuture.isWritten()){
            return true;
        }
        else {
            return false;
        }
    }
}
