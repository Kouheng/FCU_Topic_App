package com.example.user.myapplication.DB;

import android.util.Log;

import com.example.user.myapplication.Restaurant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by kiyot on 2016/10/20.
 */
public class classMethod {
    String getString;
    Restaurant restaurant = new Restaurant();
    int id;
    String time;

    //初始化資料庫
    public void CreateDefaultData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        DBMS restaurant_Data = new DBMS();
        Connection connection = restaurant_Data.getConnection();

        restaurant_Data.initialization(connection);
    }

    public void filterAll() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        DBMS restaurant_Data = new DBMS();

        Connection connection = restaurant_Data.getConnection();

        ArrayList getData = restaurant_Data.selectAllTable(connection);

        String get = getData.toString();

        restaurant.setName(get);


        out.println(restaurant.getName());
    }

    //測試插入資料
    public void dbTest(String name, String phone) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        DBMS dbms = new DBMS();
        Log.d("mytag","enter1");
        Connection connection = dbms.getConnection();
        Log.d("mytag","enter2");

        dbms.insert(connection, name, phone);
    }

    //查詢資料Method
    public static ArrayList<Restaurant> filterExact() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        DBMS dbms_data = new DBMS();
        Connection connection = dbms_data.getConnection();  //把資料庫裡面的資料鏈接功能導入
        String keyword = new String();
        ArrayList<Restaurant> restaurants;

        restaurants = dbms_data.searchFilter(connection, keyword);


        /*for (int i = 0; i < restaurants.size(); i++) {      測試印出治療
            out.print(restaurants.get(i).getRid() + " ");
            out.print(restaurants.get(i).getName() + " ");
            out.println(restaurants.get(i).getTel());
        }*/


        return restaurants; //回傳包裝的資料
    }

    //回傳飲食類型
    public ArrayList<Restaurant> restaurantsFoodType(String foodTypeSearch) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DBMS dbms_data = new DBMS();
        Connection connection = dbms_data.getConnection();
        ArrayList<Restaurant> foodTypeReturn = dbms_data.foodTypeSearch(connection, foodTypeSearch);

        for (int i = 0; i < foodTypeReturn.size(); i++) {
            //測試印出治療
            out.print(foodTypeReturn.get(i).getRid() + " ");
            out.print(foodTypeReturn.get(i).getName() + " ");
            out.println(foodTypeReturn.get(i).getAddr());
        }
        return foodTypeReturn;
    }

    //回傳飲食風格
    public ArrayList<Restaurant> restaurantsFoodStyle(String foodStyleSearch) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DBMS dbms_data = new DBMS();
        Connection connection = dbms_data.getConnection();
        ArrayList<Restaurant> foodStyleReturn = dbms_data.foodStyleSearch(connection, foodStyleSearch);

        for (int i = 0; i < foodStyleReturn.size(); i++) {
            //測試印出治療
            out.print(foodStyleReturn.get(i).getRid() + " ");
            out.print(foodStyleReturn.get(i).getName() + " ");
            out.println(foodStyleReturn.get(i).getAddr());
        }
        return foodStyleReturn;
    }

    public String conVertToString(ArrayList<String> strings) {
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string);
        }

        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static void main(String[] args) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Scanner scanner = new Scanner(System.in);
        classMethod run = new classMethod();
        // run.filterAll();
        String typing_name;
        String typing_phne;

        out.print("insert name:");
        typing_name = scanner.next();

        out.print("insert phone:");
        typing_phne = scanner.next();

        run.dbTest(typing_name,typing_phne);
    }

}