package com.rq.myapplication;

import android.app.ActivityManager;
import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        Log.i("MyApplication", "MyApplication is oncreate ======= pid= " + pid);
        String processNameString = "";
        ActivityManager activityManager = (ActivityManager) this.getSystemService(getApplicationContext().ACTIVITY_SERVICE);

        for(ActivityManager.RunningAppProcessInfo appProcessInfo: activityManager.getRunningAppProcesses()) {
            if( appProcessInfo.pid == pid) {
                processNameString = appProcessInfo.processName;
            }
        }
        // 根据每个进程不同的名称，做出不同的处理，防止重复进行初始化
        if("com.example.myprogress:remote".equals( processNameString)) {
            Log.i("MyApplication", "processName = " + processNameString + " ----- work!");
        } else {
            Log.i("MyApplication", "processName = " + processNameString + " ----- work!");
        }

    }
}
