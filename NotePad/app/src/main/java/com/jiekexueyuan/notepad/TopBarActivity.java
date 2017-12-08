package com.jiekexueyuan.notepad;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
//    private Db db;
    private String event;
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


//        db = new Db(this);
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
//        db.dbWrite(cv);
        MainActivity.getMainActivity().addNote(cv);
        MainActivity.getMainActivity().refreshListView();

        /*调用main方法的非静态方法*/

//        mainActivity.refreshListView();
        Toast.makeText(TopBarActivity.this,"添加成功",Toast.LENGTH_LONG).show();
        setReminder(true);

    }
    private static TopBarActivity topBarActivity;
    public TopBarActivity(){
        topBarActivity = this;
    }
    public static TopBarActivity getTopBarActivity(){
        return topBarActivity;
    }

    private int time;

    public void setReminder(boolean b){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,MyReceiver.class);
        time = Integer.parseInt(edTime.getText().toString());
        event = edEvent.getText().toString();
        intent.putExtra("event",event+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TopBarActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println("这是发送的广播:"+event);
        if (b){

            long firstTime = SystemClock.elapsedRealtime();
//            返回从UTC1970年1月1日夜开始经过的毫秒数；
            long systemTime = System.currentTimeMillis();
//            获取系统当前时间
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
                alarmManager.set(AlarmManager.RTC_WAKEUP, diffTime, pendingIntent);
            }

        }
        else {
            alarmManager.cancel(pendingIntent);
        }
    }


}
