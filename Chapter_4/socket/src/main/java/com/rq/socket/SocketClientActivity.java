package com.rq.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientActivity extends AppCompatActivity {

    private Button bt_send;
    private EditText et_receive;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Intent service = new Intent(this, SocketServerService.class);
        startService(service);
        new Thread() {
            @Override
            public void run() {
                connectSocketServer();
            }
        }.start();
    }

    private void initView() {
        et_receive = findViewById(R.id.et_receive);
        bt_send = findViewById(R.id.bt_send);
        tv_message = findViewById(R.id.tv_message);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String msg = et_receive.getText().toString();
                // 向服务端发送消息
                if(!TextUtils.isEmpty(msg) && null != mPrintWriter) {
                    // 不要在UI线程中进行流操作
                    new Thread(){
                        @Override
                        public void run() {
                            mPrintWriter.println(msg);
                        }
                    }.start();
                    tv_message.setText(tv_message.getText() + "\n" + "客户端：" + msg);
                    et_receive.setText("");
                }
             }
        });
    }

    private void connectSocketServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()) {
                final String msg = br.readLine();
                if(msg != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_message.setText(tv_message.getText() + "\n" + "服务端：" + msg);
                        }
                    });
                }
            }

            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}