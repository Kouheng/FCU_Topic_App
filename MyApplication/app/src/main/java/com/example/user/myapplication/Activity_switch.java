package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import static com.example.user.myapplication.R.*;
import static com.example.user.myapplication.R.layout.*;

/**
 * Created by User on 2016/6/1.
 */
public class Activity_switch extends Activity {
    Button button,button1,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState){  //將上一個頁面的資訊丟過來
        super.onCreate(savedInstanceState);  //我接 <3
        setContentView(activity_second);


        Button btn3 = (Button)this.findViewById(id.button3);  //管理者界面按鈕


        btn3.setOnClickListener(new Button.OnClickListener() { //button3 的點擊Listener
            @Override
            public void onClick(View v) {   //button3 的點擊事件
                Intent intent = new Intent();   //切換頁面
                intent.setClass(Activity_switch.this,Linking.class);
                startActivity(intent);
            }
        });


    }



}
