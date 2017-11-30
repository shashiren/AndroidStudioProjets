package com.jiekexueyuan.notepad;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class TopBarActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edTime;
    private EditText edEvent;
    private Button btnAdd;
    private DbUsage dbUsage1;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;
    private String event;
    private MainActivity mainActivity;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_bar);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        dbUsage1 = new DbUsage();
        edTime = findViewById(R.id.edt1);
        edEvent = findViewById(R.id.edt2);
        btnAdd = findViewById(R.id.btn1);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        cv.put("time",edTime.getText().toString());
        cv.put("event",edEvent.getText().toString());
        dbUsage1.dbWriteNotePad(cv);
//        dbWrite.insert("notepad",null,cv);
        dbUsage1.dbReadNotePad();
        Toast.makeText(TopBarActivity.this,"添加成功",Toast.LENGTH_LONG).show();
        setReminder(true);

    }
//    public TopBarActivity(Context context,MainActivity mainActivity){
//
//    }

    private int time;

    private void setReminder(boolean b){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,MyReceiver.class);
        time = Integer.parseInt(edTime.getText().toString());
        intent.putExtra("event",edEvent.getText().toString()+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TopBarActivity.this,0,intent,0);
        if (b){
//            获取系统当前时间
            long firstTime = SystemClock.elapsedRealtime();
//            返回从UTC1970年1月1日夜开始经过的毫秒数；
            long systemTime = System.currentTimeMillis();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.HOUR_OF_DAY,time);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
//            计算当前时间
            long selectTime = calendar.getTimeInMillis();
//            如果当前的时间大于设置时间，那么就从第二天的设定时间开始
            if(systemTime > selectTime){
                calendar.add(Calendar.DAY_OF_MONTH,1);
                selectTime = calendar.getTimeInMillis();
            }
//            计算现在时间到设定时间的时间差
            long diffTime = selectTime - systemTime;
//            系统当前时间+时间差
            long my_Time = firstTime + diffTime;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, diffTime, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, diffTime, pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, diffTime, AlarmManager.INTERVAL_DAY, pendingIntent);
            }

        }
        else {
            alarmManager.cancel(pendingIntent);
        }
    }


}
