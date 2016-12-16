package com.example.user.myapplication.DB;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.myapplication.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiyot on 2016/11/26.
 */
public class ClassMethod  {
    DBMethod dbmethod;
    public ClassMethod(Context context){
        dbmethod = new DBMethod(context); // **首要執行的程式
        try {
            Log.d("mytag", "dbme here");
            dbmethod.SyncData(); //建議放在程式初始化的地方  **次要該執行的程式
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //要拿資料就用DBListPackage()
    public List<Restaurant> DBListPackage() {
        List<Restaurant> ConnectData;

        ConnectData = dbmethod.GetAllTable();

        return ConnectData;
    }
}
