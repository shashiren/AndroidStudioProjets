package com.jiekexueyuan.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhougl on 2017/11/16.
 */

public class Db extends SQLiteOpenHelper {
    private Cursor mCursor;

        public Db(Context context) {
        super(context, "NotePad", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE notepad("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "time TEXT DEFAULT \"\","+
                    "event TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor dbRead(){
        SQLiteDatabase db = getReadableDatabase();
        mCursor = db.query("notepad", null, null, null, null, null, null);
        return mCursor;
    }

    public void dbWrite(ContentValues contentValues){
        SQLiteDatabase db = getWritableDatabase();
        db.insert("notepad", null, contentValues);

    }
    public void dbDelete(int itemId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notepad", "_id=?", new String[]{itemId+""});

    }
}