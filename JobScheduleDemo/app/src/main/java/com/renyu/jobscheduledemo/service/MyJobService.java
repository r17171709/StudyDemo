package com.renyu.jobscheduledemo.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by renyu on 2017/1/9.
 */

public class MyJobService extends JobService {

    public static final String MESSENGER_INTENT_KEY="MESSENGER_INTENT_KEY";
    public static final String WORK_DURATION_KEY="WORK_DURATION_KEY";
    public static final int MSG_COLOR_START=1;
    public static final int MSG_COLOR_PROCESS=3;
    public static final int MSG_COLOR_STOP=2;

    Messenger messenger;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null) {
            messenger=intent.getParcelableExtra("MESSENGER_INTENT_KEY");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        sendMessage(MSG_COLOR_START, params);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(MSG_COLOR_PROCESS, params);
                jobFinished(params, false);
            }
        }, 3000);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        sendMessage(MSG_COLOR_STOP, params);
        return false;
    }

    private void sendMessage(int messageId, JobParameters params) {
        Message message=new Message();
        message.arg1=messageId;
        message.what=params.getJobId();
        message.obj=params.getExtras().getString(MyJobService.WORK_DURATION_KEY);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
