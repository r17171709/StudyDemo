package com.clevo.androidminademo.utils;

/**
 * Created by Clevo on 2016/11/1.
 */

public class Params {

    //连接超时时间
    public static final int CONNECT_TIMEOUT=30000;
    //长连接发送频率
    public static final int REQUEST_INTERVAL=10;
    //应答超时
    public static final int REQUEST_TIMEOUT=5;
    //IP
    public static final String HOSTNAME="192.168.1.106";
    //端口号
    public static final int PORT = 4444;
    //心跳包发送
    public static final String SEND="ping_send";
    //心跳包接受
    public static final String RECEIVE="ping_receive";
}
