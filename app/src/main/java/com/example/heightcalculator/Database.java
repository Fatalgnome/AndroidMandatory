package com.example.heightcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    //The name of the database file
    private static final String DATABASE_NAME = "heightDB.db";

    //We will have one table, called "products"
    private static final String TABLE_HEIGHTS = "heigts";

    //We will have 3 columns in this table
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VALUE = "value";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_HEIGHTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_VALUE
                + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEIGHTS);
        onCreate(db);
    }

    public ArrayList<Values> getAllHeights()
    {
        ArrayList<Values> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM "+TABLE_HEIGHTS+" ORDER BY id DESC", null);

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String value = cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
            Values v = new Values(id, value);
            list.add(v);
        }
        cursor.close();
        db.close();

        return list;
    }

    public void addValue(Values value)
    {
        ContentValues values = new ContentValues();

        values.put(COLUMN_VALUE, value.getValue());

        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(TABLE_HEIGHTS, null, values);
        value.setID(id);
        db.close();
    }
}
