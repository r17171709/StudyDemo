package com.clevo.androidminademo;

import android.app.IntentService;
import android.content.Intent;

import com.clevo.androidminademo.utils.PushManager;

/**
 * Created by Clevo on 2016/11/1.
 */

public class PushService extends IntentService {

    public PushService() {
        super("PushService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PushManager.getInstance().connect();
    }
}
