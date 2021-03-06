package com.example.user.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.myapplication.DB.ClassMethod;
import com.example.user.myapplication.DB.DBMethod;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.myapplication.R.*;
import static com.example.user.myapplication.R.layout.*;

public class Linking extends AppCompatActivity {
    public Linking() {

    }

    TextView textView , textView2;
    Button button, button2, button4, button5;
    EditText editText;
    CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;

    boolean checkBoxState, checkBox2State;  //紀錄狀態用的,在此不用宣告final
    static boolean textSearchFlag = false;      //文字搜尋開啟
    static boolean sensorFlag = false;         //體感的flag
    static boolean debugFlag = false;         //靜態資料的/debug模式
    //TODO debug模式和一些資料定義在這裡改  而且有全空的0000編碼

    List<Restaurant> restaurant_list = new ArrayList<Restaurant>();      //list
    ClassMethod classMethod;            //我們的資料庫哦哦哦哦哦哦


    /*這邊體感專用區*/
    private SensorManager mSensorManager;   //體感(Sensor)使用管理
    private Sensor mSensor;                 //體感(Sensor)類別
    private float mLastX;                    //x軸體感(Sensor)偏移
    private float mLastY;                    //y軸體感(Sensor)偏移
    private float mLastZ;                    //z軸體感(Sensor)偏移
    private double mSpeed;                 //甩動力道數度
    private long mLastUpdateTime;           //觸發時間

    private static final int SPEED_SHRESHOLD = 800;        //甩動力道數度設定值 (數值越大需甩動越大力，數值越小輕輕甩動即會觸發)
    private static final int UPTATE_INTERVAL_TIME = 200;     //觸發間隔時間
    /*體感專用區結束*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);      //這三行設定標題顏色 參考書籤
        setContentView(activity_main);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, layout.mytitle);

        textView = (TextView) findViewById(id.textView);
        textView2 = (TextView) findViewById(id.textView2);  //連結到網頁
        button = (Button) findViewById(id.button);
        button2 = (Button) findViewById(id.button2);
        button4 = (Button) findViewById(id.button4);
        button5 = (Button) findViewById(id.button5);
        editText = (EditText) findViewById(id.editText);
        checkBox = (CheckBox) findViewById(id.checkBox);
        checkBox2 = (CheckBox) findViewById(id.checkBox2);
        checkBox3 = (CheckBox) findViewById(id.checkBox3);
        checkBox4 = (CheckBox) findViewById(id.checkBox4);
        checkBox5 = (CheckBox) findViewById(id.checkBox5);
        checkBox6 = (CheckBox) findViewById(id.checkBox6);

        final RelativeLayout background = (RelativeLayout)findViewById(R.id.back);
        background.setBackgroundColor(0xFFFFF8D7);          //這兩行設定背景顏色



        final ListView listV = (ListView) findViewById(id.listView);

        String text2 = "連結到網頁";
        final String url = "http://36.235.91.48/test";
        textView.setText("今天吃什麼");
        textView2.setText(Html.fromHtml("<br/><br/><a href=\""+ url +"/\">"+ text2 +"</a>"));

        /*以下體感*/
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);  //取得體感(Sensor)服務使用權限
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   //取得手機Sensor狀態設定
        mSensorManager.registerListener(SensorListener, mSensor, SensorManager.SENSOR_DELAY_GAME);  //註冊體感(Sensor)甩動觸發Listener
        /*體感結束*/





        //button4.setVisibility(View.GONE);




        /*設定list*/
        /*final MyAdapter adapter = new MyAdapter(Linking.this, restaurant_list);
        listV.setAdapter(adapter);*/


        Log.d("mytag","run");
        new Thread(new Runnable() {

            final Dialog dialog = ProgressDialog.show(Linking.this,
                    "讀取中", "嘗試資料同步中...",true);
            @Override
            public void run() {
                try {
                    setDB();
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dialog.dismiss();



                    createData();     //設定資料
                    Log.d("mytag","onRunEnd");
                }
            }
        }).start();


        /*list的Listener*/                                                                        //單項list row的點擊事件
        SetListListener(listV);
        SetCheckBoxClick();
        SetOnButtonClick(listV);    //監看按鈕
        SetTextListener(url);
        SetEditTextListener(listV);

        /*初始化*/
        initialization(listV);

    }
    //onCreate end


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(Linking.this)
                    .setTitle("確認視窗")
                    .setMessage("確定要返回嗎?")
                    .setIcon(drawable.question_mark_icon)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            }).show();
        }
        return true;
    }

    /*單項list row的點擊事件 List的Listener*/
    public void SetListListener(final ListView listV) {
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 點下去的動作
                Intent intent = new Intent();
                intent.setClass(Linking.this, ListRow_Detail.class);  //用Intent 設定當前頁面,與目標頁面

                /*以下傳資料*/
                Adapter adapter = listV.getAdapter();
                Object rowObject = adapter.getItem(position);
                String name = ((Restaurant) rowObject).getName();     //淦他媽的終於能夠取得自定義物件的值了
                String addr = ((Restaurant) rowObject).getAddr();
                String openTime = ((Restaurant) rowObject).getOpenTime();
                String tel = ((Restaurant) rowObject).getTel();
                String web = ((Restaurant) rowObject).getWeb();
                String foodType = ((Restaurant) rowObject).getFoodType();

                //Log.d("mytag", "Value is: "+name + addr);                             //測試能不能抓到值

                intent.putExtra("name", name);
                intent.putExtra("addr", addr);
                intent.putExtra("openTime", openTime);
                intent.putExtra("tel", tel);
                intent.putExtra("web", web);
                intent.putExtra("foodType", foodType);

                if (!addr.equals(""))               //這個條件判斷是用在避免你點進去ㄘㄉ
                    startActivity(intent);  //切換

            }
        });

    }



    /**
     * 用來判斷checkBox的狀態，決定是否顯示checkBox3,4和決定搜尋的結果
     */
    public void SetCheckBoxClick() {
        //CheckBox狀態 : 未勾選，隱藏

        /*紀錄狀態用的一號和二號，checkboxState宣告全域在上面*/
        /*這是一號*/
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checkBoxIsChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                //紀錄狀態
                checkBoxState = checkBoxIsChecked;
                if (checkBoxState && checkBox2State)
                    checkBox2.setChecked(false);        //互斥選項

                checkBoxlogic();

            }
        });

        /* 這是二號*/
        checkBox2.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checkBoxIsChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                //紀錄狀態
                checkBox2State = checkBoxIsChecked;
                if (checkBoxState && checkBox2State)
                    checkBox.setChecked(false);             //互斥選項

                checkBoxlogic();
            }
        });


    }

    /*checkBox邏輯判斷，用來設定按鈕的互斥*/
    public void checkBoxlogic() {

        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
        /*都沒按下則隱藏底下的多的選項*/
        if (!checkBoxState && !checkBox2State) {   //輕鬆初始化+取消顯示
            checkBox3.setVisibility(View.GONE);
            checkBox4.setVisibility(View.GONE);
            checkBox5.setVisibility(View.GONE);
            checkBox6.setVisibility(View.GONE);
        }
        /*按下ㄘㄉ*/
        else if (checkBoxState) {
            checkBox3.setText("中餐");
            checkBox4.setText("西餐");
            checkBox5.setText("日式");
            checkBox6.setText("韓式");

            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
            checkBox6.setVisibility(View.VISIBLE);
            //SystemClock.sleep(100);
        }
        /*按下ㄏㄉ*/
        else {
            checkBox3.setText("茶飲");
            checkBox4.setText("咖啡");
            checkBox5.setText("酒類");

            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
            checkBox6.setVisibility(View.GONE);

            //SystemClock.sleep(100);
        }

    }

    /* 取得全部的checkBox的狀態  回傳字串給foodtype比較*/
    public String getCheckBoxStateALL() {
        String stateCode = "";
        stateCode = getCheckBoxStateSingle(checkBox, stateCode);
        stateCode = getCheckBoxStateSingle(checkBox2, stateCode);
        stateCode = getCheckBoxStateSingle(checkBox3, stateCode);
        stateCode = getCheckBoxStateSingle(checkBox4, stateCode);
        stateCode = getCheckBoxStateSingle(checkBox5, stateCode);
        stateCode = getCheckBoxStateSingle(checkBox6, stateCode);

        return stateCode;
    }

    /*取得單一的checkBox狀態*/
    private String getCheckBoxStateSingle(CheckBox checkBox, String stateCode) {
        if (checkBox.isChecked()) stateCode += "1";
        else stateCode += "0";

        return stateCode;
    }

    /**
     * 這個超重要  搜尋就在這裡做了
     */
    public void search(final ListView listV) {
        setData(listV);
        List<Restaurant> temp_list = new ArrayList<Restaurant>();      //list

        Adapter adapter = listV.getAdapter();  //先把list要用的東西拿出來
        String searchName = editText.getText().toString().trim();   //拿到搜尋用的文字
        boolean nameFlag;  //有無符合文字搜尋

        Log.d("mytag", "list總量 : " + adapter.getCount());
        for (int position = 0; position < adapter.getCount(); position++) {


            Object rowObject = adapter.getItem(position);
            Restaurant resTemp = (Restaurant) rowObject;    //先做強制轉型
            nameFlag = true;        //先設定有符合  如要比較再給F
            if (!searchName.equals("")) {       //如果有輸入再搜尋name
                Log.d("mytag", "name");
                nameFlag = false;
                String name = resTemp.getName();
                if (name.contains(searchName)) nameFlag = true;  //符合包含字串
            }

            // 接著進行標籤搜尋
            if (nameFlag) {
                //Log.d("mytag", getCheckBoxStateALL() + " and " + resTemp.getFoodType());

                if (foodTypeSearch(resTemp.getFoodType())) //符合條件就放進去
                    temp_list.add((Restaurant) rowObject);
            }
        }

        if (!"".equals(searchName)) {
            Toast.makeText(getApplicationContext(), "搜尋 " + searchName + " 的結果", Toast.LENGTH_SHORT).show();             //     文字搜尋
        }

        MyAdapter myAdapterTemp = new MyAdapter(Linking.this, temp_list);
        listV.setAdapter(myAdapterTemp);
        setRestaurantType(listV);
    }

    /**
     * foodtype的邏輯  在這裡實作標籤的包含
     * 邏輯說明 : 使用者未勾選 = 0  意義為don't care  舉例來說使用者沒有勾"吃的"  那麼不管有沒有"吃的"標籤都會被加入
     * 但如果使用者有勾選 = 1  意義為要進行比對  如果使用者有勾"喝的"  那就一定要有標籤"喝的"
     */
    public boolean foodTypeSearch(String s) {
        String check = getCheckBoxStateALL();       //勾選的狀態/標籤
        int s_length = s.length();                  //拿兩個字串的長度
        int check_length = check.length();

        if (s_length < check_length){
            for (int i = 0; i < (check_length - s_length); i++) //長度不符補0
                s += "0";
        }

        else if (s_length > check_length){
            for (int i = 0; i < s_length-check_length; i++) //長度不符補0
                check += "0";
        }

        for (int i = 0; i < check.length(); i++) {
            switch (check.charAt(i)) {
                case '1'://進行比對
                    if (check.charAt(i) != s.charAt(i)) return false;
                    break;
                case '0'://don't care
                    break;
                default:
                    Log.d("mytag", "something wrong with foodType switch");
            } //switch

        }//for i

        return true;
    }

    /*這裡可以設定list的顏色避免亂掉  Type 1 = 條目/標題  Type0/2 = 兩種顏色*/
    public void setRestaurantType(ListView listV){
        Adapter adapter = listV.getAdapter();  //先把list要用的東西拿出來
        boolean typeFlag = false;

        for (int position = 0; position < adapter.getCount(); position++) {

            Object rowObject = adapter.getItem(position);
            Restaurant resTemp = (Restaurant) rowObject;    //先做強制轉型

            if (resTemp.getType() != 1){  //判斷不是條目
                if (!typeFlag) {
                    resTemp.setType(0);     //給定顏色
                    typeFlag = true;
                }
                else{
                    resTemp.setType(2);     //另一個顏色
                    typeFlag = false;
                }

            }
            else typeFlag = false;      //如果是條目就重置flag 避免跟條目同顏色
        }

    }


    /**
     * 用來監看按鈕有無按下，包括 button,button2,button4並進行後續動作
     */
    public void SetOnButtonClick(final ListView listV) {  //監聽按鈕事件
        button.setOnClickListener(new View.OnClickListener() {//設定當觸發後   此按鈕為接收資料
            @Override
            public void onClick(View v) {//override
                //搜尋按鈕的實作
                search(listV);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {//設定當觸發後
            @Override
            public void onClick(View v) {//override                                                                           /* 要做潮好用的初始化*/

                initialization(listV);
                Toast.makeText(getApplicationContext(), "RE:從零開始_初始化", Toast.LENGTH_SHORT).show();
            }
        });

        /*文字搜尋用的按鈕4，下次請記得toast要show出來*/
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//override    //或者透過打字搜尋
                if (textSearchFlag) {
                    button4.setText("文字搜尋");
                    editText.setText(null);   //清空
                    editText.setVisibility(View.GONE);
                    textSearchFlag = false;
                } else {
                    button4.setText("取消");
                    editText.setVisibility(View.VISIBLE);
                    textSearchFlag = true;
                }
            }
        });

        /*全部列出的按鈕*/
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(listV);
                Toast.makeText(getApplicationContext(), "\\搖搖有隨機哦/", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetTextListener(final String url){
        textView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Uri u = Uri.parse(url);
                Intent it = new Intent(Intent.ACTION_VIEW, u);
                Linking.this.startActivity(it);
            }
        });
    }
    public void SetEditTextListener(final ListView listV){
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               //按下完成鍵要執行的動作
               search(listV);
               return false;
           }
       });
    }
    /*超強大初始化!*/
    public void initialization(ListView listV) {
        /*初始化選項*/
        checkBox.setChecked(false);
        checkBox2.setChecked(false);

        checkBoxlogic();
        editText.setVisibility(View.GONE);
        editText.setText(null);     //清空吧
        button4.setText("文字搜尋");
        textSearchFlag = false;
        sensorFlag = false;

        /*也要把清單重置*/
        List<Restaurant> null_list = new ArrayList<Restaurant>();
        MyAdapter adapter = new MyAdapter(Linking.this, null_list);
        listV.setAdapter(adapter);

    }

    /*體感豪豪玩*/


    SensorEventListener SensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent mSensorEvent) {
            //當前觸發時間
            long mCurrentUpdateTime = System.currentTimeMillis();

            //觸發間隔時間 = 當前觸發時間 - 上次觸發時間
            long mTimeInterval = mCurrentUpdateTime - mLastUpdateTime;

            //若觸發間隔時間< 70 則return;
            if (mTimeInterval < UPTATE_INTERVAL_TIME) return;

            mLastUpdateTime = mCurrentUpdateTime;

            //取得xyz體感(Sensor)偏移
            float x = mSensorEvent.values[0];
            float y = mSensorEvent.values[1];
            float z = mSensorEvent.values[2];

            //甩動偏移速度 = xyz體感(Sensor)偏移 - 上次xyz體感(Sensor)偏移
            float mDeltaX = x - mLastX;
            float mDeltaY = y - mLastY;
            float mDeltaZ = z - mLastZ;

            mLastX = x;
            mLastY = y;
            mLastZ = z;

            //體感(Sensor)甩動力道速度公式
            mSpeed = Math.sqrt(mDeltaX * mDeltaX + mDeltaY * mDeltaY + mDeltaZ * mDeltaZ) / mTimeInterval * 10000;

            //若體感(Sensor)甩動速度大於等於甩動設定值則進入 (達到甩動力道及速度)
            if (mSpeed >= SPEED_SHRESHOLD) {

                if (!sensorFlag) {
                    //達到搖一搖甩動後要做的事情
                    //Log.d("TAG","搖一搖中...");  //這個會顯示在編譯器裡
                    //  體感修改區

                    editText.setText("");

                    ListView listV = (ListView) findViewById(id.listView);   //對xml裡對應的list做修改

                    List<Restaurant> temp_list = new ArrayList<>();
                    Restaurant resTemp;

                    setData(listV);  //全部列出的資料
                    search(listV);  //拿去做搜尋
                    Adapter temp_adapter = listV.getAdapter();

                    int length = temp_adapter.getCount();  //取得長度
                    if (length > 2) {       //至少要大於兩個條目+兩個暫時沒有資料的長度
                        do {
                            int random = (int) (Math.random() * length);   //random 產生0~1的double  乘上長度再強制轉型 就會變成0~長度


                            Object rowObject = temp_adapter.getItem(random);

                            resTemp = (Restaurant) rowObject;


                            Log.d("mytag", resTemp.getName() + " random");
                        } while (resTemp.getType() == 1);             //這是為了避免骰到條目  像吃的喝的

                        temp_list.add(resTemp);  //把抓到的隨機丟進去
                        Log.d("mytag", "not null here");
                        MyAdapter adapter = new MyAdapter(Linking.this, temp_list);   //設到listView
                        listV.setAdapter(adapter);


                        if (!getCheckBoxStateALL().contains("1"))
                            Toast.makeText(getApplicationContext(), "隨機雷你!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "根據勾選標籤的隨機結果的隨機雷你!", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "資料過少或沒有資料!!", Toast.LENGTH_SHORT).show();


                    sensorFlag = true;
                }


            } else
                sensorFlag = false; //連續搖動結束後取消
        }

        @Override
        public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

        }

    };

    @Override
    protected void onDestroy() //在程式關閉時移除體感(Sensor)觸發
    {
        super.onDestroy();
        mSensorManager.unregisterListener(SensorListener);
    }

    /*體感不豪玩*/

    /*此處建立資料  可以加入debug判斷來決定是否要從資料庫拿*/
    private void createData() {

        /* (type , name , addr , time,tel,web,foodTypeCode)
                    foodType "XXXXXX" : 6碼 1:吃的  2:喝的 3:中式/茶飲  4:西式/咖啡  5:日式/酒類  6:韓式 */
        if (debugFlag) {
            //restaurant_list.add(new Restaurant(1, "\\好吃在哪裡/", "", "", "", "", "101111"));
            restaurant_list.add(new Restaurant(0, "85度C", "台中市西屯區河南路二段282號", "12:00~21:00", "0934865978", "", "0101"));
            restaurant_list.add(new Restaurant(2, "一宸的愉悅炒飯", "台中市西屯區西安街277巷77弄1號", "13:00~20:00", "0954865978", "https://www.facebook.com/profile.php?id=100000394006035", "101001"));
            restaurant_list.add(new Restaurant(0, "麥當勞", "台中市西屯區福星路427號", "12:00~20:00", "0954568478", "http://www.mcdonalds.com.tw/", "1001"));

            //restaurant_list.add(new Restaurant(1, "飲品", "", "", "", "", "011111"));
            restaurant_list.add(new Restaurant(0, "藍天", "台中市西屯區文華路", "07:00~12:00", "0918765978", "", "0111"));
            restaurant_list.add(new Restaurant(2, "世界茶", "台中市西屯區文華路", "09:00~20:00", "0954867758", "", "0110"));

            restaurant_list.add(new Restaurant(0, "有間餐廳", "24.183947, 120.647869", "11:00~22:00", "0912345678", "", "1010"));
        }
        else{
            restaurant_list = classMethod.DBListPackage();     //從資料庫抓資料
        }
        sortList();
    }

    /*排序資料*/
    private void sortList(){
        List<Restaurant> temp_list = new ArrayList<>();
        Restaurant resTemp;
        temp_list.add(new Restaurant(1, "\\好吃在哪裡/", "", "", "", "", "101111"));

        for (int i = 0; i < restaurant_list.size(); i++) {      //把吃的丟進去
            resTemp = restaurant_list.get(i);
            if (resTemp.getFoodType().startsWith("10") && resTemp.getType() != 1)
                temp_list.add(resTemp);
        }
        if (temp_list.size() == 1){     //沒有吃的資料
            temp_list.clear();
        }
        temp_list.add(new Restaurant(1, "飲品", "", "", "", "", "011111"));
        for (int i = 0; i < restaurant_list.size(); i++) {
            resTemp = restaurant_list.get(i);
            if (resTemp.getFoodType().startsWith("01") && resTemp.getType() != 1)
                temp_list.add(resTemp);
        }
        if (temp_list.size() == 1){
            temp_list.clear();
            temp_list.add(new Restaurant(1, "暫時沒有資料哦!", "", "", "", "", "101111"));
        }


        restaurant_list = temp_list;
        Log.d("mytag","sorted");
    }

    /*資料全部列出*/
    private void setData(ListView listV) {
        MyAdapter adapter = new MyAdapter(Linking.this, restaurant_list);
        listV.setAdapter(adapter);
        setRestaurantType(listV);
    }

    /*同步資料庫  onCreate有寫一個runable來等他*/
    private void setDB(){
        classMethod = new ClassMethod(this);
    }

}