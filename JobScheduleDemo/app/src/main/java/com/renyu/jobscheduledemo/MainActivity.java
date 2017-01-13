package com.renyu.jobscheduledemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.renyu.jobscheduledemo.service.MyJobService;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    JobScheduler service;

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d("MainActivity", "getJobId:" + msg.what + " " + msg.arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service= (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        Intent intent=new Intent(this, MyJobService.class);
        Messenger messenger=new Messenger(handler);
        intent.putExtra(MyJobService.MESSENGER_INTENT_KEY, messenger);
        startService(intent);

        final Random random=new Random();
        addJob(random.nextInt(1000)+100, 5000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                addJob(random.nextInt(1000)+100, 5000);
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                service.cancelAll();
            }
        }, 6000);
    }

    private void addJob(int jobId, int MinimumLatency) {
        PersistableBundle bundle=new PersistableBundle();
        bundle.putString(MyJobService.WORK_DURATION_KEY, "HELLO");
        JobInfo.Builder builder=new JobInfo.Builder(jobId, new ComponentName(getPackageName(), MyJobService.class.getName()));
//        builder.setPeriodic(3000);
        builder.setMinimumLatency(MinimumLatency);
//        builder.setOverrideDeadline(OverrideDeadline);
        builder.setExtras(bundle);
        builder.setPersisted(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        int result=service.schedule(builder.build());
        Log.d("MainActivity", "result:" + result);
    }
}
