package com.jiekexueyuan.notepad;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyAlarm extends AppCompatActivity {

    public static final int NOTIFICATION_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_alarm);


        final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification n = new Notification();
        n.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"20");
        nm.notify(NOTIFICATION_ID,n);

        Intent i = new Intent();


        TextView tv = findViewById(R.id.tvNotification);
        tv.setText(i.getExtras().toString());
        Button btnCancel =  findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm.cancel(NOTIFICATION_ID);
                finish();
            }
        });
    }

//    private void notification (){
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.notification_icon)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");
//// Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, ResultActivity.class);
//
//// The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//// mId allows you to update the notification later on.
//        mNotificationManager.notify(mId, mBuilder.build());
//    }
}
