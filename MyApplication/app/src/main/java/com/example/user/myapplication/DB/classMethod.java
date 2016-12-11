package jayon.com.eatwhat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiyot on 2016/11/26.
 */
public class ClassMethod extends AppCompatActivity {

    DBMethod dbmethod = new DBMethod(this);
    private Context MethodContext;
    private Button createTable, dropTable, addData, search_filter;
    private EditText insertName, insertPhone, editTable, foodStyle, foodType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbmethod.OpenDB();

        //Button Listener 作為測試可自行更改
        createTable = (Button) findViewById(R.id.create_table);
        dropTable = (Button) findViewById(R.id.drop_table);
        addData = (Button) findViewById(R.id.add_data);
        search_filter = (Button) findViewById(R.id.search_data);

        insertName = (EditText) findViewById(R.id.insert_name);
        insertPhone = (EditText) findViewById(R.id.insert_phone);
        editTable = (EditText) findViewById(R.id.edit_table);
        foodType = (EditText) findViewById(R.id.food_type);
        foodStyle = (EditText) findViewById(R.id.food_style);

        createTable.setOnClickListener(CreateTable);
        dropTable.setOnClickListener(DropTable);
        search_filter.setOnClickListener(SearchData);
    }

    //手動建立Table
    private Button.OnClickListener CreateTable = new Button.OnClickListener() {
        public void onClick(View view) {
            String table_name = editTable.getText().toString();
            dbmethod.CreateDBTable(table_name);
        }
    };

    //刪除Table
    private Button.OnClickListener DropTable = new Button.OnClickListener() {
        public void onClick(View v) {
            String table_name = editTable.getText().toString();
            dbmethod.DropDBTable(table_name);

        }
    };

    //搜尋資料
    private Button.OnClickListener SearchData = new Button.OnClickListener() {
        public void onClick(View v) {

            List<Restaurant> ConnectData = new ArrayList<>();

            ConnectData = dbmethod.GetAllTable();

            for (int i = 0; i < ConnectData.size(); i++) {

                Log.e("Tag", String.valueOf(ConnectData.get(i).getRid()) + " " +
                        String.valueOf(ConnectData.get(i).getName()) + " " +
                        String.valueOf(ConnectData.get(i).getAddress()) + " " +
                        String.valueOf(ConnectData.get(i).getTel()) + " " +
                        String.valueOf(ConnectData.get(i).getTime()) + " " +
                        String.valueOf(ConnectData.get(i).getPicture()) + " " +
                        String.valueOf(ConnectData.get(i).getScore()) + " " +
                        String.valueOf(ConnectData.get(i).getTag()));
            }
            /*
            String type = foodType.getText().toString();
            FoodTypeSearch(type);
            */
        }
    };

    //Listener 觸發所指定的模糊搜尋
    public ArrayList<Restaurant> FilterExact(String keyword) {
        ArrayList<Restaurant> storePackage;

        storePackage = dbmethod.SearchFilter(keyword);

        /*for (int i = 0; i < restaurants.size(); i++) {      測試印出治療
            out.print(restaurants.get(i).getRid() + " ");
            out.print(restaurants.get(i).getName() + " ");
            out.println(restaurants.get(i).getTel());
        }*/
        return storePackage; //回傳包裝的資料
    }

    //Listener 觸發所指定的標籤查詢
    public ArrayList<Restaurant> FoodTypeSearch(String label) {
        ArrayList<Restaurant> storePackage;

        storePackage = dbmethod.foodTypeSearch(label);

        return storePackage;
    }

    //包裝資料表
    public List<Restaurant> DBListPackage() {
        List<Restaurant> ConnectData = new ArrayList<>();

        ConnectData = dbmethod.GetAllTable();

        return ConnectData;
    }
}
