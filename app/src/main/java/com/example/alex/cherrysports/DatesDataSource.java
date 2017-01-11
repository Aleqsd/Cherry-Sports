package com.example.alex.cherrysports;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alex on 11/01/2017.
 * DataSource de notre DB
 */

class DatesDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DATE };

    DatesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    Date createDate(String date) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        long insertId = database.insert(MySQLiteHelper.TABLE_DATES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DATES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Date newDate = cursorToDate(cursor);
        cursor.close();
        return newDate;
    }

    void deleteDate(Date date) {
        long id = date.getId();
        System.out.println("Date deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_DATES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    List<Date> getAllDates() {
        List<Date> dates = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DATES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Date date = cursorToDate(cursor);
            dates.add(date);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return dates;
    }

    private Date cursorToDate(Cursor cursor) {
        Date date = new Date();
        date.setId(cursor.getLong(0));
        date.setDate(cursor.getString(1));
        return date;
    }
}