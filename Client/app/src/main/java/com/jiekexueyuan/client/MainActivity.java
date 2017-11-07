package com.jiekexueyuan.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiekexueyuan.server.IAppServiceRemoteBinder;
import com.jiekexueyuan.server.TimerServiceCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Intent serviceIntent;
    private TextView tvCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*可能报错*/
        tvCallback = findViewById(R.id.tvCallback);

        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.jiekexueyuan.server", "com.jiekexueyuan.server.AppService"));

        findViewById(R.id.btnBindServer).setOnClickListener(this);
        findViewById(R.id.btnUnbindServer).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBindServer:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindServer:
                unbindService(this);
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        System.out.println("Bind Service");
        System.out.println(iBinder);

        binder = IAppServiceRemoteBinder.Stub.asInterface(iBinder);
        try {
            binder.registCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

        callUnRegistBinder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callUnRegistBinder();
    }

    private void callUnRegistBinder(){
        try {
            binder.unRegistCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private TimerServiceCallback onServiceCallback = new TimerServiceCallback.Stub() {
        @Override
        public void onTimer(int numIndex) throws RemoteException {

            Message msg = new Message();
            msg.obj = MainActivity.this;
            msg.arg1 = numIndex;
            hander.sendMessage(msg);

        }
    };

    private final MyHander hander = new MyHander();

    private class MyHander extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int index = msg.arg1;
            MainActivity _this = (MainActivity) msg.obj;
            _this.tvCallback.setText(index);
        }
    }

    private IAppServiceRemoteBinder binder = null;
}
