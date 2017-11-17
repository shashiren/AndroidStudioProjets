package com.jiekexueyuan.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhougl on 2017/11/16.
 */

public class Db extends SQLiteOpenHelper {
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
}
