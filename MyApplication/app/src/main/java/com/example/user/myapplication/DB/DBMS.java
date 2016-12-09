package com.example.user.myapplication.DB;

/**
 * Created by kiyot on 2016/10/17.
 */

import android.util.Log;

import com.example.user.myapplication.Restaurant;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBMS extends Restaurant {
    public Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Connection dbTestconn = null;

        SQLiteConfig config = new SQLiteConfig();
        // config.setReadOnly(true);
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);

        SQLiteDataSource ds = new SQLiteDataSource(config);
        ds.setUrl("jdbc:sqlite:newtest.db");
        return ds.getConnection();
    }

    //初始化資料庫--store_table
    public void initialization(Connection connection) throws SQLException {
        String sqlCreateTable = "DROP TABLE IF EXISTS store_table ;create table store_table " +
                "(id integer primary key, name char(30), address char(60), phone char(11)" +
                "time char(20), picture char(50), score DOUBLE, foodType char(20), foodStyle char(20)); ";

        Statement stat = null;
        stat = connection.createStatement();
        stat.executeUpdate(sqlCreateTable);
    }

    //創建
    public void createTable(Connection connection, String Table) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + Table + " ;create table " + Table + " (id integer primary key, name char(30)); ";
        Statement stat = null;
        stat = connection.createStatement();
        stat.executeUpdate(sql);
    }

    //刪除table
    public void dropTable(Connection connection, String Table) throws SQLException {
        String sql = "drop table" + Table;
        Statement stat = null;
        stat = connection.createStatement();
        stat.executeUpdate(sql);
    }

    //新增
    public void insert(Connection connection, String name, String phone) throws SQLException {

        String sql = "insert into store_table (name,phone) values(?,?)";
        PreparedStatement pst = null;
        pst = connection.prepareStatement(sql);
        int idx = 1;
        pst.setString(idx++, name);
        pst.setString(idx++, phone);
        pst.executeUpdate();
    }

    //修改
    public void update(Connection connection, int id, String name) throws SQLException {
        String sql = "update store_table set name = ? where id = ?";
        PreparedStatement pst = null;
        pst = connection.prepareStatement(sql);
        int idx = 1;
        pst.setString(idx++, name);
        pst.setInt(idx++, id);
        pst.executeUpdate();
    }

    //刪除
    public void delete(Connection connection, int id) throws SQLException {
        String sql = "delete from store_table where id = ?";
        PreparedStatement pst = null;
        pst = connection.prepareStatement(sql);
        int idx = 1;
        pst.setInt(idx++, id);
        pst.executeUpdate();
    }

    public ArrayList<String> selectAllTable(Connection connection) throws SQLException {
        ArrayList<String> listView = new ArrayList();
        String sql = "select * from store_table";
        String getS;
        Statement stat;
        ResultSet rs;
        stat = connection.createStatement();
        rs = stat.executeQuery(sql);
        while (rs.next()) {

            getS = rs.getString("name");

            listView.add(getS);

        }
        return listView;
    }

    //查詢
    public ArrayList<Restaurant> searchFilter(Connection connection, String name) throws SQLException {
        ArrayList<Restaurant> listView = new ArrayList();
        String sql = "select * from store_table where name LIKE '" + name + "%'";
        String getName;
        String getPhone;
        int getId;
        Statement stat;
        ResultSet rs;
        stat = connection.createStatement();
        rs = stat.executeQuery(sql);
        while (rs.next()) {

            getId = rs.getInt("id");
            getName = rs.getString("name");
            getPhone = rs.getString("phone");

            listView.add(new Restaurant(getId, getName, getPhone));

        }
        return listView;
    }

    public ArrayList<Restaurant> foodTypeSearch(Connection connection, String food_type) throws SQLException {
        ArrayList<Restaurant> foodTypeList = new ArrayList<>();
        String sql = "select * from store_table where foodType LIKE '" + food_type + "'";
        String getName;
        String getPhone;
        int getId;
        Statement stat;
        ResultSet rs;
        stat = connection.createStatement();
        rs = stat.executeQuery(sql);
        while (rs.next()) {

            getId = rs.getInt("id");
            getName = rs.getString("name");
            getPhone = rs.getString("phone");

            foodTypeList.add(new Restaurant(getId, getName, getPhone));

        }
        return foodTypeList;
    }

    public ArrayList<Restaurant> foodStyleSearch(Connection connection, String food_style) throws SQLException {
        ArrayList<Restaurant> foodStyleList = new ArrayList<>();
        String sql = "select * from store_table where foodStyle LIKE '" + food_style + "'";
        String getName;
        String getPhone;
        int getId;
        Statement stat;
        ResultSet rs;
        stat = connection.createStatement();
        rs = stat.executeQuery(sql);
        while (rs.next()) {

            getId = rs.getInt("id");
            getName = rs.getString("name");
            getPhone = rs.getString("phone");

            foodStyleList.add(new Restaurant(getId, getName, getPhone));

        }
        return foodStyleList;
    }
}

