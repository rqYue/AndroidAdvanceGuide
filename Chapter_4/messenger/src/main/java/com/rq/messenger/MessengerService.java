package com.rq.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import java.security.PublicKey;

public class MessengerService extends Service {

    public static final String TAG = "MoonMessenger";
    public static final int MSG_FROM_CLIENT = 1000;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.i(TAG, "收到客户端消息--------" + msg.getData().get("msg"));

                    // 得到客户端传来的 Messenger 对象
                    Messenger messenger = msg.replyTo;
                    Message message = Message.obtain(null, MSG_FROM_CLIENT);

                    Bundle mBundle = new Bundle();
                    mBundle.putString("rep", "这里是服务器，已接收信息");
                    message.setData(mBundle);

                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(mHandler).getBinder();
    }
}