package com.example.user.myapplication.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiyot on 2016/11/20.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "eatwhat.db";

    private final static String TableName = "store_table";
    private final static String RowId = "id";
    private final static String Name = "name";
    private final static String Address = "address";
    private final static String Phone = "phone";
    private final static String Time = "time";
    private final static String Score = "score";
    private final static String Price = "price";
    private final static String FoodType = "foodType";
    private final static String FoodStyle = "foodStyle";
    private final static String Blog = "blog";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override

    //初始化資料庫
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TableName + " (" + RowId + " integer primary key, "
                + Name + " char(30), " + Address + " char(60), " + Phone + " char(11), "
                + Time + " char(20), " + Score + " double, " + Price + " char(20), "
                + FoodType + " char(20), " + FoodStyle + " char(20), " + Blog + " char(250))";

        sqLiteDatabase.execSQL(sql);
    }

    public void reCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TableName + " (" + RowId + " integer primary key, "
                + Name + " char(30), " + Address + " char(60), " + Phone + " char(11), "
                + Time + " char(20), " + Score + " double, " + Price + " char(20), "
                + FoodType + " char(20), " + FoodStyle + " char(20), " + Blog + " char(250))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override

    //資料更動
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TableName;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
