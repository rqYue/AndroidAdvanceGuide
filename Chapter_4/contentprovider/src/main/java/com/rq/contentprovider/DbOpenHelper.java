package com.rq.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "game_provider.db";
    static final String GAME_TABLE_NAME = "game";
    private static final int DB_VERSION = 1;

    private String CREATE_GAME_TABLE = "create table if not exists " + GAME_TABLE_NAME
            + "(_id integer primary key," + "name TEXT, " + "describe TEXT)";

    public DbOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
