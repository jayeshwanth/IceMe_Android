/**
 * Created by jaggu on 4/18/2015.
 */
package com.uconnect.events.database.sqilte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uconnect.events.app.UCAppConstants;

/**
 * UCDatabaseHelper helps to create the local sqlite database.
 */
public class UCDatabaseHelper extends SQLiteOpenHelper implements UCAppConstants {

    /**
     *
     * @param context
     */
    public UCDatabaseHelper(Context context) {
        super(context, UCSQLiteDB.DATABASE_NAME, null, UCSQLiteDB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
