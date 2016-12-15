package com.example.user.myapplication.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.example.user.myapplication.Restaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DBMethod {
    private DBHelper DH = null;
    public String[] inputData;

    private Context context;

    public DBMethod(Context context) {
        this.context = context;
    }

    //同步資料庫的內部處理
    private class subTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            DH = new DBHelper(context);

            //創建資料庫
            SQLiteDatabase db = DH.getWritableDatabase();
            try {
                inputData = new String[10];
                URL url = new URL("http://36.233.50.249/test/transferjson.php");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                db.execSQL("DROP TABLE IF EXISTS " + "store_table");
                DH.reCreate(db);
                Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    int i = 0;
                    String totext = Html.fromHtml(line).toString();
                    Matcher matcher = pattern.matcher(totext);
                    while (matcher.find()) {
                        inputData[i] = matcher.group(1);
                        i++;
                        if (i == 10) {
                            int id = Integer.parseInt(inputData[1]);
                            Double score = Double.parseDouble(inputData[7]);
                            String name = inputData[0];
                            String address = inputData[4];
                            String phone = inputData[5];
                            String time = inputData[6];
                            String price = inputData[8];
                            String foodType = inputData[3];
                            String foodStyle = inputData[2];
                            String blog = inputData[9];

                            InsertDBTable(id, name, address, phone, time, foodType, foodStyle, score, price, blog);

                            i = 0;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("print", "failed");
            }
            return null;
        }
    }

    //呼叫同步資料庫的執行Method
    public void SyncData() throws IOException {
        new subTask().execute();
    }

    //線上同步導入到本地資料庫
    public void InsertDBTable(int id, String name, String address, String phone, String time,
                              String foodType, String foodStyle, Double score, String price,
                              String blog) {

        SQLiteDatabase db = DH.getWritableDatabase();

        String sql = "insert into store_table (id,name,address,phone,time,score,price,foodType," +
                "foodStyle,blog) values('" + id + "','" + name + "', '" + address + "','" + phone + "','"
                + time + "','" + score + "','" + price + "','" + foodType + "','"
                + foodStyle + "','" + blog + "')";

        db.execSQL(sql);
    }

    //導出資料庫裝在List裡面
    public List<Restaurant> GetAllTable() {

        int position_foodType = 7, postion_foodStyle = 8;
        SQLiteDatabase db = DH.getReadableDatabase();
        List<Restaurant> ResultStore = new ArrayList<>();
        String sql = "select * from store_table";
        int DBid;
        String DBname, DBtel, DBaddress, DBtime, DBweb;
        String tokens;
        Cursor searchData;

        searchData = db.rawQuery(sql, null);

        String eatTag[] = new String[6];
        String drinkTag[] = new String[6];
        eatTag[0] = "吃的";
        eatTag[1] = "";
        eatTag[2] = "中餐";
        eatTag[3] = "西餐";
        eatTag[4] = "日式";
        eatTag[5] = "韓式";

        drinkTag[0] = "";
        drinkTag[1] = "喝的";
        drinkTag[2] = "茶飲";
        drinkTag[3] = "咖啡";
        drinkTag[4] = "酒類";
        drinkTag[5] = "";

        while (searchData.moveToNext()) {
            //TODO hashmap
            HashMap<String, Integer> hash_food = new HashMap<>();
            int decode[] = new int[6];
            String valueDecode;

            //對應格式標記標籤
            for (int i = position_foodType; i <= postion_foodStyle /*size*/ ; i++) {
                String key = searchData.getString(i);  // key  = e.g. "eat"   or "drink"
                hash_food.put(key, 1);   //hash (key, value)
            }
            //搜尋Hash裡面的key
            for (HashMap.Entry<String, Integer> entry : hash_food.entrySet()) {
                String tagKey = entry.getKey();
                int i = 0;

                //當arrayTag陣列裡面的字串相對hash的key值時，回傳1
                while (i < eatTag.length) {
                    if (eatTag[i].equals(tagKey) || drinkTag[i].equals(tagKey)) {
                        //只有在陣列值為0的時候才執行
                        if (decode[i] != 1) {
                            decode[i] = 1;
                        }
                    } else {
                        if (decode[i] != 1) {
                            decode[i] = 0;
                        }
                    }
                    i++;
                }
            }
            valueDecode = Arrays.toString(decode);

            tokens = valueDecode.replaceAll("\\[", "").replaceAll("\\]", "").
                    replaceAll(",", "").replaceAll(" ","");

            DBid = searchData.getInt(searchData.getColumnIndex("id"));
            DBname = searchData.getString(searchData.getColumnIndex("name"));
            DBaddress = searchData.getString(searchData.getColumnIndex("address"));
            DBtel = searchData.getString(searchData.getColumnIndex("phone"));
            DBtime = searchData.getString(searchData.getColumnIndex("time"));
            DBweb = searchData.getString(searchData.getColumnIndex("blog"));


            ResultStore.add(new Restaurant(DBid, DBname, DBaddress, DBtime, DBtel, DBweb, tokens));
        }
        return ResultStore;
    }

}