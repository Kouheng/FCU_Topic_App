package com.example.user.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.user.myapplication.R.layout.list_row;
import static com.example.user.myapplication.R.id;

/**
 * Created by user on 2016/11/13.
 * 這裡是list裡面單行點進去的詳細資料，會用作連結到網頁，MAP等
 */
public class ListRow_Detail extends AppCompatActivity {

    TextView textName, textAddr, textLink , textOpenTime , textTel ,textMap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //將上一個頁面的資訊丟過來
        super.onCreate(savedInstanceState);  //我接 <3
        setContentView(list_row);      //畫面設置

        textName = (TextView) findViewById(id.row_name);   //name
        textAddr = (TextView) findViewById(id.row_addr);  //addr
        textLink = (TextView) findViewById(id.row_link);  //網頁
        textOpenTime = (TextView) findViewById(id.row_openTime);
        textTel = (TextView) findViewById(id.row_tel);
        imageView = (ImageView) findViewById(id.imageView);
        textMap = (TextView) findViewById(id.textView5);

        String name, addr, openTime, tel, web;
        Bundle params = getIntent().getExtras();  //拆包囉拆包囉~

        name = params.getString ("name");
        addr = params.getString ("addr");
        openTime = params.getString("openTime");
        tel = params.getString ("tel");
        web = params.getString ("web");          //網址
        String foodType = params.getString("foodType");

        //textMap.setText(foodType);                            //測試餐廳類型用
        SetOnImageViewClick(name, addr);

        textName.setText(name);
        textAddr.setText(addr);
        textOpenTime.setText(openTime);
        textTel.setText(tel);

        assert web != null;   //assert = 斷言  斷言web一定不為空   為空則跳出錯誤   維護用的非必要code
        if (!web.equals(""))
            textLink.setText(Html.fromHtml("<big><b>相關網頁與部落格</b></big>，網址如下：<br/><br/><a href=\""+ web +"/\">"+ web +"</a>"));
        else
            textLink.setText(Html.fromHtml("<big><b>暫無相關網頁與部落格</b></big>"));

    }


    //TODO 把按鈕改成其他東西吧
    public void SetOnImageViewClick(final String name, final String addr) {  //監聽按鈕事件
        imageView.setOnClickListener(new View.OnClickListener() {//設定當觸發後   此按鈕為接收資料
            @Override
            public void onClick(View v) {//override
                //搜尋按鈕的實作

                //http://maps.google.com.tw/maps?f=q&hl=zh-TW&geocode=&q=輸入查詢的地址&z=比例大小&output=embed&t=地圖模式
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "http://maps.google.com.tw/maps?f=q&hl=zh-TW&geocode=&q="+ addr +"&z=16&output=embed&t="));
                //TODO 上面的網址在接資料庫時要把座標改成addr
                startActivity(i);

            }
        });
        textMap.setOnClickListener(new View.OnClickListener() {//設定當觸發後   此按鈕為接收資料
            @Override
            public void onClick(View v) {//override
                //搜尋按鈕的實作

                //http://maps.google.com.tw/maps?f=q&hl=zh-TW&geocode=&q=輸入查詢的地址&z=比例大小&output=embed&t=地圖模式
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "http://maps.google.com.tw/maps?f=q&hl=zh-TW&geocode=&q="+ addr +"&z=16&output=embed&t="));
                //TODO 上面的網址在接資料庫時要把座標改成addr
                startActivity(i);

            }
        });
    }

}
