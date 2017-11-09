package com.jiekexueyuan.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class AppService extends Service {

    private boolean running = false;
    private int numIndex = 0;
    private RemoteCallbackList<TimerServiceCallback> callbackList = new RemoteCallbackList<>();


    public AppService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return new IAppServiceRemoteBinder.Stub() {

            @Override
            public void setData(String data) throws RemoteException {

            }

            @Override
            public void registCallback(TimerServiceCallback callback) throws RemoteException {

                callbackList.register(callback);
            }

            @Override
            public void unRegistCallback(TimerServiceCallback callback) throws RemoteException {

                callbackList.unregister(callback);
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                running = true;

                for (numIndex = 0;running;numIndex++) {

                    int count = callbackList.beginBroadcast();

                    while (count-- > 0) {
                        try {
                            callbackList.getBroadcastItem(count).onTimer(numIndex);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    callbackList.finishBroadcast();

                    try {
                        Thread.sleep(1000);
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

}
