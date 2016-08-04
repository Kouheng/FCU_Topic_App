package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import static com.example.user.myapplication.R.*;
import static com.example.user.myapplication.R.layout.*;

/**
 * Created by User on 2016/6/1.
 */
public class MainActivity extends Activity {

    TextView textView;
    LinearLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState){  //將上一個頁面的資訊丟過來
        super.onCreate(savedInstanceState);  //我接 <3
        setContentView(activity_blink);      //畫面設置   MainActivity -> activity_blink
        textView = (TextView) findViewById(id.textView4);
        layout1 = (LinearLayout) findViewById(id.linearLayout1);

        layout1.setOnClickListener(new LinearLayout.OnClickListener(){    //設定整個layout接收點擊的事件


            @Override
            public void onClick(View v) {   //點擊
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_switch.class);  //用Intent 設定當前頁面,與目標頁面
                startActivity(intent);  //切換
            }
        });





    }



}
