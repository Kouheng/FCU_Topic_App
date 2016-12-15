package com.example.user.myapplication;

import java.util.HashMap;

/**
 * 定義餐廳的資料型態
 * Created by user on 2016/10/15.
 *
 */
public class Restaurant {
    private int type;   //顯示的資料類型
    private int Rid;     //餐廳的ID
    private String name;
    private String addr;
    private String web = ""; //網址
    private String openTime = "";
    private String tel = "";
    private String foodType;   //foodType "XXXXXX" : 6碼 1:吃的  2:喝的 3:中式/茶飲  4:西式/咖啡  5:日式/酒類  6:韓式


    public Restaurant(){

    }
    
    public Restaurant(int type, String name, String addr, String openTime , String tel, String web,String foodType)
    {
        this.type = type;
        this.name = name;
        this.addr = addr;
        this.openTime = openTime;
        this.tel = tel;
        this.web = web;
        this.foodType = foodType;
    }

    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }
    public int getType(){
        if (type >= 0 && type <= 2)
            return type;
        else return 0;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getRid(){
        return Rid;
    }
    public void setRid(int rid){
        this.Rid = rid;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getAddr(){
        return addr;
    }
    public void setAddr(String addr){
        this.addr = addr;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
