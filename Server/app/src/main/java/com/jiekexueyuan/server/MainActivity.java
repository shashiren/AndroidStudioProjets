package com.jiekexueyuan.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private AppService.Binder binder = null;
    private IAppServiceRemoteBinder binder1 = null;
    private TextView tvOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOut = findViewById(R.id.tv1);

        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBindService:
                bindService(new Intent(this,AppService.class),this, Context.BIND_AUTO_CREATE);

                break;
            case R.id.btnUnbindService:
                unbindService(this);
                break;
        }

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {


//        binder = (AppService.Binder) iBinder;
        binder1 = IAppServiceRemoteBinder.Stub.asInterface(iBinder);
//        binder.gteService().setCallback(new AppService.Callback() {
//            @Override
//            public void onDataChange(String numIndex) {
//                Message msg = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("numIndex",numIndex);
//                msg.setData(bundle);
//                handler.sendMessage(msg);
//            }
//        });
        try {
            binder1.registCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("bindservice");
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
            binder1.unRegistCallback(onServiceCallback);
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
            handler.sendMessage(msg);

        }
    };

    private final MyHandler handler = new MyHandler();

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int index = msg.arg1;
            MainActivity _this = (MainActivity) msg.obj;
            _this.tvOut.setText("回调数据"+index);
        }
    }

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            tvOut.setText(msg.getData().getString("numIndex"));
//        }
//    };
}
