package com.rq.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ContentProviderActivity extends AppCompatActivity {

    private final static String TAG = "ContentProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = Uri.parse("content://com.rq.contentprovider.GameProvider");

        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 2);
        contentValues.put("name", "崩坏");
        contentValues.put("describe", "星穹铁道");

        getContentResolver().insert(uri, contentValues);

        Cursor gameCursor = getContentResolver().query(uri, new String[]{"name", "describe"}, null, null, null);
        while( gameCursor.moveToNext()) {
            Game mGame = new Game(gameCursor.getString(0), gameCursor.getString(1));

            Log.i(TAG, mGame.gameName + " ----- " + mGame.gameDescribe);
        }
    }
}