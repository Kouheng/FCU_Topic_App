package jayon.com.eatwhat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiyot on 2016/11/20.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "eatwhat.db";

    private final static String TableName = "store_table";
    private final static String RowId = "id";
    private final static String Name = "name";
    private final static String Address = "address";
    private final static String Phone = "phone";
    private final static String Time = "time";
    private final static String Picture = "picture";
    private final static String Score = "score";
    private final static String FoodType = "foodType";
    private final static String FoodStyle = "foodStyle";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override

    //初始化資料庫
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TableName + " (" + RowId + " integer primary key, "
                + Name + " char(30), " + Address + " char(60), " + Phone + " char(11), "
                + Time + " char(20), " + Picture + " char(50), " + Score + " double, "
                + FoodType + " char(20), " + FoodStyle + " char(20))";

        sqLiteDatabase.execSQL(sql);
    }

    @Override

    //資料更動
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS" + TableName;

        sqLiteDatabase.execSQL(sql);
    }
}
