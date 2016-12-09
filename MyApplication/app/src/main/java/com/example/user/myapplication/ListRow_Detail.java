package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import static com.example.user.myapplication.R.layout.list_row;
import static com.example.user.myapplication.R.id;

/**
 * Created by user on 2016/11/13.
 * 這裡是list裡面單行點進去的詳細資料，會用作連結到網頁，MAP等
 */
public class ListRow_Detail extends AppCompatActivity {

    TextView textName, textAddr, textLink , textOpenTime , textTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {  //將上一個頁面的資訊丟過來
        super.onCreate(savedInstanceState);  //我接 <3
        setContentView(list_row);      //畫面設置

        textName = (TextView) findViewById(id.row_name);   //name
        textAddr = (TextView) findViewById(id.row_addr);  //addr
        textLink = (TextView) findViewById(id.row_link);  //網頁
        textOpenTime = (TextView) findViewById(id.row_openTime);
        textTel = (TextView) findViewById(id.row_tel);

        Bundle params = getIntent().getExtras();  //拆包囉拆包囉~

        textName.setText(params.getString ("name"));
        textAddr.setText(params.getString ("addr"));
        textOpenTime.setText(params.getString("openTime"));
        textTel.setText(params.getString ("tel"));

        String web = params.getString ("web");          //網址

        textLink.setText(Html.fromHtml("<big><font color=\"#FF0000\"><b>相關網頁與部落格</b></font></big>，網址如下：<br/><br/><a href=\""+ web +"/\">"+ web +"</a>"));


    }

}
