package com.jiekexueyuan.notepad;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddNote extends AppCompatActivity implements View.OnClickListener {

    private EditText edTime;
    private EditText edEvent;
    private Button btnAdd;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;
    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        edTime = findViewById(R.id.edt1);
        edEvent = findViewById(R.id.edt2);
        btnAdd = findViewById(R.id.btn1);
        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite =db.getWritableDatabase();

        adapter = new SimpleCursorAdapter(this,R.layout.note_list_cell,null,new String[]{"time","event"},new int[]{R.id.tv1,R.id.tv2});
        btnAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        ContentValues cv = new ContentValues();
        cv.put("time",edTime.getText().toString());
        cv.put("enent",edEvent.getText().toString());

        dbWrite.insert("notepad",null,cv);
        Toast.makeText(AddNote.this,"添加成功",Toast.LENGTH_LONG).show();
        refreshListView();

    }

    private void refreshListView(){
        Cursor c = dbRead.query("notepad",null,null,null, null,null,null);
        adapter.changeCursor(c);


    }
}
