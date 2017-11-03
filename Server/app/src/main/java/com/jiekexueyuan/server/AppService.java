package com.jiekexueyuan.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppService extends Service {

    private boolean running = false;
    private String data = null;


    public AppService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return new Binder();
    }

    public class Binder extends android.os.Binder{

        public void setData(String data){
            AppService.this.data = data ;
        }
        public AppService gteService(){
            return AppService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        running = true;
        new Thread(){
            @Override
            public void run() {
                super.run();

                int i = 0;
                while (running){

                    i++;

                    String str = i+":"+data;
                    System.out.println(str);

                    if (callback!=null){
                        callback.onDataChange(str);
                    }
                        try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        System.out.println("Service destoryed");
    }

    private Callback callback = null;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallback() {
        return callback;
    }

    public static interface Callback{

        void onDataChange(String data);
    }
}
