package com.jiekexueyuan.notepad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(context,MyAlarm.class);
        context.startActivity(intent);
        Intent i = new Intent();
        System.out.println("接受到了消息");
    }
}