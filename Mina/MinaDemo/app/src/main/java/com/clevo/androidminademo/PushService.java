package com.clevo.androidminademo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.clevo.androidminademo.utils.Params;
import com.clevo.androidminademo.utils.PushManager;
import com.koushikdutta.async.AsyncNetworkSocket;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;

import static android.R.id.message;

/**
 * Created by Clevo on 2016/11/1.
 */

public class PushService extends IntentService {

    public PushService() {
        super("PushService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Mina库
//        PushManager.getInstance().connect();

        AsyncServer.getDefault().connectSocket(Params.HOSTNAME, Params.PORT, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                if (ex!=null) {
                    Log.d("PushService", "连接出错");
                    return;
                }
                socket.setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        Log.d("PushService", new String(bb.getAllByteArray()));
                    }
                });
                socket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        if (ex!=null) {
                            Log.d("PushService", "setClosedCallback出错");
                            return;
                        }
                        Log.d("PushService", "setClosedCallback");
                    }
                });
                socket.setEndCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        if (ex!=null) {
                            Log.d("PushService", "setEndCallback出错");
                            return;
                        }
                        Log.d("PushService", "setEndCallback");
                    }
                });
                socket.setWriteableCallback(new WritableCallback() {
                    @Override
                    public void onWriteable() {
                        Log.d("PushService", "onWriteable");
                    }
                });
                Util.writeAll(socket, "Client\r\n".getBytes(), new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        if (ex!=null) {
                            Log.d("PushService", "writeAll出错");
                            return;
                        }
                        Log.d("PushService", "writeAll");
                    }
                });
            }
        });
    }
}
