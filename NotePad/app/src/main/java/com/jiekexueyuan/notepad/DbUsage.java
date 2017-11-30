package com.jiekexueyuan.notepad;

/**
 * Created by stan on 2017/11/29.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUsage {

    private MainActivity mainActivity;
    private Context context;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite,dbDelete;
    private ContentValues contentValues;

//    public DbUsage(Context context,MainActivity mainActivity){
//        this.setContext(context);
//        this.setMainActivity(mainActivity);
//    }
    public void dbReadNotePad(){
        db = new Db(context);
         dbRead = db.getReadableDatabase();
         Cursor cursor = dbRead.query("notepad",null,null,null, null,null,null);
         mainActivity.refreshListView(cursor);
    }
    public void dbWriteNotePad(ContentValues contentValues ){
        db = new Db(context);
        dbWrite = db.getWritableDatabase();
        dbWrite.insert("notepad",null,contentValues);
    }
    public void dbDeleteNotePad(int itemId){
        db = new Db(context);
        dbDelete = db.getWritableDatabase();
        dbDelete.delete("notepad","_id=?",new String[]{itemId+""});
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Context getContext() {
        return context;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setDbRead(SQLiteDatabase dbRead) {
        this.dbRead = dbRead;
    }

    public SQLiteDatabase getDbRead() {
        return dbRead;
    }

    public void setDbWrite(SQLiteDatabase dbWrite) {
        this.dbWrite = dbWrite;
    }

    public SQLiteDatabase getDbWrite() {
        return dbWrite;
    }
}
