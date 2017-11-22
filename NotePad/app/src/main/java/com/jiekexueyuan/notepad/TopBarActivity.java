package com.jiekexueyuan.notepad;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;

public class TopBarActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edTime;
    private EditText edEvent;
    private Button btnAdd;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;
    private String event;
    private SimpleCursorAdapter adapter;

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

        edTime = findViewById(R.id.edt1);
        edEvent = findViewById(R.id.edt2);
        btnAdd = findViewById(R.id.btn1);
        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite =db.getWritableDatabase();

//        adapter = new SimpleCursorAdapter(this,R.layout.note_list_cell,null,new String[]{"time","event"},new int[]{R.id.tv1,R.id.tv2});


        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        cv.put("time",edTime.getText().toString());
        cv.put("event",edEvent.getText().toString());
        dbWrite.insert("notepad",null,cv);
        Toast.makeText(TopBarActivity.this,"添加成功",Toast.LENGTH_LONG).show();
        setReminder(true);
//        refreshListView();

    }

    private void setReminder(boolean b){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,MyReceiver.class);
        intent.putExtra("event",edEvent.getText().toString()+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TopBarActivity.this,0,intent,0);
        if (b){
            Calendar calendar = Calendar.getInstance();
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
        else {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void refreshListView(){
        Cursor c = dbRead.query("notepad",null,null,null, null,null,null);
        adapter.changeCursor(c);

    }
}
