package com.example.bebabeba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samuel Mathai on 6/16/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Bebabeba.db";
    public static final String TABLE_NAME = "DriverInfo";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PHONE_NUMBER";
    public static final String COL_4 = "LOCALITY";
    public static final String COL_5 = "LATITUDE";
    public static final String COL_6 = "LONGITUDE";

    public SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHONE_NUMBER TEXT, LOCALITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }
    public boolean insertData (String name, String phone_no, String locality){

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, phone_no);
        contentValues.put(COL_4, locality);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData (){
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME , null);
        return res;
    }

    public Cursor getDriverData (String slocality){
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from '"+ TABLE_NAME +"' where LOCALITY = '"+ slocality.trim() +"'", null);
        return res;
    }

   public boolean update (String id, String name, String surname, String marks){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return  true;
    }

    public void clearSqlLite(){
        db = this.getWritableDatabase();
/*        db.execSQL("DELETE FROM "+ TABLE_NAME);
        db.execSQL("VACUUM");*/
        db.delete(TABLE_NAME, null,null);
        db.execSQL("VACUUM");
    }

}
