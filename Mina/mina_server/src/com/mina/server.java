package com.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Clevo on 2016/11/1.
 */
public class server {

    public static void main(String[] args) {
        try {
            // 创建一个非阻塞的server端的Socket
            IoAcceptor acceptor = new NioSocketAcceptor();
            // 设置过滤器（使用Mina提供的文本换行符编解码器）
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                            .forName("UTF-8"),
                            LineDelimiter.WINDOWS.getValue(),
                            LineDelimiter.WINDOWS.getValue())));
            // 设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(2048);
            // 读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            acceptor.getFilterChain().addLast("keepalive", new KeepAliveFilter(new ServerKeepAliveMessageFactoryImp()));
            // 绑定逻辑处理器
            acceptor.setHandler(new Demo1ServerHandler());
            // 绑定端口
            acceptor.bind(new InetSocketAddress(4444));
            System.out.println("服务端启动成功...     端口号为：" + 4444);
        } catch (Exception e) {
            System.out.println("服务端启动异常....");
            e.printStackTrace();
        }
    }

}
