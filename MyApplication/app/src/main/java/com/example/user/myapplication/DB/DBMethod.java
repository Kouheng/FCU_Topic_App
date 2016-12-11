package jayon.com.eatwhat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class DBMethod {
    private DBHelper DH = null;
    private Button create_table;
    private Button drop_table;
    private Button add_data;
    private EditText insert_name, insert_phone, editTable;

    private Context context;

    public DBMethod(Context context) {
        this.context = context;
    }

    public void OpenDB() {
        DH = new DBHelper(context);

        //創建資料庫
        DH.getWritableDatabase();
    }

    //新增TABLE
    public void CreateDBTable(String table_name) {
        SQLiteDatabase db = DH.getWritableDatabase();
        String sql = "create table " + table_name + " (id integer primary key, name char(30))";
        db.execSQL("DROP TABLE IF EXISTS Test");
        db.execSQL(sql);
    }

    //刪除TABLE
    public void DropDBTable(String table_name) {
        SQLiteDatabase db = DH.getWritableDatabase();
        String sql = "drop table " + table_name;
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e(TAG, "DROP FAILED");
        }
    }

    //新增資料
    public void InsertDBTable(String name, String phone) {
        name = insert_name.getText().toString();
        phone = insert_phone.getText().toString();

        SQLiteDatabase db = DH.getWritableDatabase();

        String sql = "insert into store_table (name,phone) values('" + name + "','" + phone + "')";

        db.execSQL(sql);
    }

    public void update() {

    }

    //刪除資料
    public void delete(int id) {
        SQLiteDatabase db = DH.getWritableDatabase();
        String sql = "delete from store_table where id = " + id;

        db.execSQL(sql);
    }

    //模糊查詢
    public ArrayList<Restaurant> SearchFilter(String name) {
        SQLiteDatabase db = DH.getReadableDatabase();
        ArrayList<Restaurant> ResultStore = new ArrayList<Restaurant>();
        Cursor searchData;
        String sql = "select * from store_table where name LIKE '" + name + "%'";
        String getName;
        String getPhone;
        int getId;
        searchData = db.rawQuery(sql, null);
        while (searchData.moveToNext()) {

            getId = searchData.getInt(0);
            getName = searchData.getString(searchData.getColumnIndex("name"));
            getPhone = searchData.getString(searchData.getColumnIndex("phone"));

            // ResultStore.add(new Restaurant(getId, getName, getPhone));

        }
        return ResultStore;
    }

    //吃喝種類查詢
    public ArrayList<Restaurant> foodTypeSearch(String food_type) {
        SQLiteDatabase db = DH.getReadableDatabase();
        ArrayList<Restaurant> ResultStore = new ArrayList<>();
        Cursor searchData;
        String sql = "select * from store_table where foodType LIKE '" + food_type + "'";
        String getName;
        String getPhone;
        int getId;
        searchData = db.rawQuery(sql, null);
        while (searchData.moveToNext()) {

            getId = searchData.getInt(0);
            getName = searchData.getString(searchData.getColumnIndex("name"));
            getPhone = searchData.getString(searchData.getColumnIndex("phone"));

            // ResultStore.add(new Restaurant(getId, getName, getPhone));

        }
        return ResultStore;

    }

    //風格標籤查詢
    public ArrayList<Restaurant> foodStyleSearch(String food_style) {
        SQLiteDatabase db = DH.getReadableDatabase();
        ArrayList<Restaurant> ResultStore = new ArrayList<>();
        String sql = "select * from store_table where foodStyle LIKE '" + food_style + "'";
        Cursor searchData;
        String getName;
        String getPhone;
        int getId;
        searchData = db.rawQuery(sql, null);
        while (searchData.moveToNext()) {

            getId = searchData.getInt(0);
            getName = searchData.getString(searchData.getColumnIndex("name"));
            getPhone = searchData.getString(searchData.getColumnIndex("phone"));

            //ResultStore.add(new Restaurant(getId, getName, getPhone));

        }
        return ResultStore;
    }

    public List<Restaurant> GetAllTable() {

        int position_foodType = 7, postion_foodStyle = 8;
        int eat = 0, drink = 1, chinese = 2, western = 3;
        SQLiteDatabase db = DH.getReadableDatabase();
        List<Restaurant> ResultStore = new ArrayList<>();
        String sql = "select * from store_table";
        String foodtype, foodstyle;
        String ConvertString;
        int DBid;
        String DBname, DBtel, DBaddress, DBtime, DBpicture, DBtag;
        double DBscore;
        Cursor searchData;

        searchData = db.rawQuery(sql, null);

        String arrayTag[] = new String[4];
        arrayTag[0] = "eat";
        arrayTag[1] = "drink";
        arrayTag[2] = "chinese";
        arrayTag[3] = "western";

        while (searchData.moveToNext()) {
            //TODO hashmap
            HashMap<String, Integer> hash_food = new HashMap<>();
            int decode[] = new int[4];
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
                while (i < arrayTag.length) {
                    if (arrayTag[i].equals(tagKey)) {
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

            DBid = searchData.getInt(searchData.getColumnIndex("id"));
            DBname = searchData.getString(searchData.getColumnIndex("name"));
            DBaddress = searchData.getString(searchData.getColumnIndex("address"));
            DBtel = searchData.getString(searchData.getColumnIndex("phone"));
            DBtime = searchData.getString(searchData.getColumnIndex("time"));
            DBpicture = searchData.getString(searchData.getColumnIndex("picture"));
            DBscore = searchData.getDouble(searchData.getColumnIndex("score"));

            ResultStore.add(new Restaurant(DBid, DBname, DBaddress, DBtel, DBtime, DBpicture, DBscore, valueDecode));
        }
        return ResultStore;
    }

}