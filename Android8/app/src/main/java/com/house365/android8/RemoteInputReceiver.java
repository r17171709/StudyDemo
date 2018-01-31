package com.house365.android8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

import com.renyu.commonlibrary.commonutils.NotificationUtils;

/**
 * Created by Administrator on 2018/1/29.
 */

public class RemoteInputReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = RemoteInput.getResultsFromIntent(intent);
        if (bundle!=null) {
            String reply = bundle.getCharSequence(NotificationUtils.KEY_TEXT_REPLY)==null?"":bundle.getCharSequence(NotificationUtils.KEY_TEXT_REPLY).toString();
            NotificationUtils.getNotificationCenter(context).createNormalNotification("ticker", "channel1", reply, Color.RED, R.mipmap.ic_launcher, R.mipmap.ic_launcher, new Intent(), 105);
        }
    }
}
