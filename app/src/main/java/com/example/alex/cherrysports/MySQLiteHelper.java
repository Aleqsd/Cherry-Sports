package com.example.alex.cherrysports;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alex on 11/01/2017.
 * Classe de la DB contenant les dates
 */

class MySQLiteHelper extends SQLiteOpenHelper {

    static final String TABLE_DATES = "dates";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "dates.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DATES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DATE
            + " text not null);";

    MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATES);
        onCreate(db);
    }
}