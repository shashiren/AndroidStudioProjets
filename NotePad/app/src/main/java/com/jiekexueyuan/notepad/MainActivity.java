package com.jiekexueyuan.notepad;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ListView listView;
//    private Db db;
//    private SQLiteDatabase dbRead,dbWrite;
    private SimpleCursorAdapter adapter;
    private ClipData.Item item;
    private TextView textView1;
    private TextView textView2;
    private DbUsage dbUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        textView1 = findViewById(R.id.tv1);
        textView2 = findViewById(R.id.tv2);
        listView = findViewById(R.id.lv);
        dbUsage = new DbUsage();


//        db = new Db(this);
//        dbRead = db.getReadableDatabase();
//        dbWrite = db.getWritableDatabase();
        adapter = new SimpleCursorAdapter(this,R.layout.note_list_cell,null,new String[]{"time","event"},new int[]{R.id.tv1,R.id.tv2});
        listView.setAdapter(adapter);
        dbUsage.dbReadNotePad();
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addEvent:
                Intent intent = new Intent(MainActivity.this,TopBarActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshListView(Cursor cursor){

//        dbUsage.dbReadNotePad(cursor);
//        Cursor c = dbRead.query("notepad",null,null,null, null,null,null);
        adapter.changeCursor(cursor);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("系统提示!");
        builder1.setMessage("你确定要删除数据?");
        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);
                int itemId = cursor.getInt(cursor.getColumnIndex("_id"));
                dbUsage.dbDeleteNotePad(itemId);
//                dbWrite.delete("notepad","_id=?",new String[]{itemId+""});
                dbUsage.dbReadNotePad();
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_LONG).show();
            }
        });
        builder1.show();
        return true;
    }
}

