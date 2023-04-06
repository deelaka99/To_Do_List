package com.deelaka.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ToDoList.db";
    public static final String TABLE_NAME = "list_table";
    public static final String col1 = "ID";
    public static final String col2 = "TITLE";
    public static final String col3 = "DESCRIPTION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
        + " TITLE TEXT, DESCRIPTION TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    public boolean insertData(String title, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, title);
        contentValues.put(col3, description);
        long results = db.insert(TABLE_NAME, null,contentValues);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";",null);
        return results;
    }

    public boolean updateData(String id, String title, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1,id);
        contentValues.put(col2,title);
        contentValues.put(col3,description);
        db.update(TABLE_NAME,contentValues,"id = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[]{id});
    }
}
