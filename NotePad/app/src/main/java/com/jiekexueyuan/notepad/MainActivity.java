package com.jiekexueyuan.notepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private EditText edTime;
    private EditText edEvent;
    private Button btnAdd;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite =db.getWritableDatabase();


        adapter = new SimpleCursorAdapter(this,R.layout.note_list_cell,null,new String[]{"time","event"},new int[]{R.id.tv1,R.id.tv2});

        refreshListView();
    }

    private void refreshListView(){
        Cursor c = dbRead.query("notepad",null,null,null, null,null,null);
        adapter.changeCursor(c);


    }
}