package com.mina;

import com.koushikdutta.async.*;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by renyu on 2016/12/8.
 */
public class AsyncAndroidServer {

    public static void main(String[] args) {
        System.out.println("====================Server==================");
        ServerSocket ss;
        Socket serverSocket;
        try {
            ss = new ServerSocket(4445);
            serverSocket= ss.accept();
            System.out.println("--------------some guest connected----------------");

            InputStream is = serverSocket.getInputStream();
            InputStreamReader isr =new InputStreamReader(is);
            BufferedReader br =new BufferedReader(isr);
            String info =null;
            while((info=br.readLine())!=null){
                System.out.println("我是服务器，客户端说："+info);
            }

//            OutputStream os=serverSocket.getOutputStream();
//            os.write("server".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
