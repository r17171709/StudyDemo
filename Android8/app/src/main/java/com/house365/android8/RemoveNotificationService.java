package com.house365.android8;

import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by renyu on 2018/1/30.
 */

@RequiresApi (api = Build.VERSION_CODES.O)
public class RemoveNotificationService extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);
        Log.d("onNotificationPosted", sbn.toString());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        super.onNotificationRemoved(sbn, rankingMap, reason);
        Log.d("onNotificationRemoved", sbn.toString());
    }
}
