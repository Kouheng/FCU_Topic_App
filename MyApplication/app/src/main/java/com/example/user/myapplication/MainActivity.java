package com.example.user.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
 * 一個開始頁的概念
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
                final Intent intent = new Intent();
                intent.setClass(MainActivity.this,Linking.class);  //用Intent 設定當前頁面,與目標頁面

                new Thread(new Runnable() {final Dialog dialog = ProgressDialog.show(MainActivity.this,
                        "讀取中", "請稍候...",true);

                    @Override
                    public void run() {
                        try {

                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            dialog.dismiss();

                            startActivity(intent);  //切換

                        }
                    }
                }).start();

            }
        });





    }



}
