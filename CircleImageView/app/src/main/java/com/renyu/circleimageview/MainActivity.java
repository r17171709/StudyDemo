package com.renyu.circleimageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //七牛上传文件代码
//        String token="Dqpz1Cpn1Vq114fZ-MWuGayyv1Tsi1c0HRxJcMJs:f0aB1TC4k_iyfKWc8pUbEYElfjA=:eyJzY29wZSI6Im1vcm5pbmd0ZWwiLCJkZWFkbGluZSI6MTQyMDY4OTA3Mn0=";
//        UploadOptions options=new UploadOptions(null, "key", false, new UpProgressHandler() {
//            @Override
//            public void progress(String s, double v) {
//                System.out.println("key:"+v);
//            }
//        }, null);
//
//        UploadManager manager=new UploadManager();
//        manager.put(new File("/storage/sdcard1/MPAY_SSD.SYS"), "file", token, new UpCompletionHandler() {
//            @Override
//            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
//                System.out.println(s+" "+responseInfo.error+" "+jsonObject.toString());
//            }
//        }, options);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
