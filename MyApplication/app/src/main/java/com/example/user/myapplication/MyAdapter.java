package com.example.user.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * list的接口實作，用於連接listView和自定義的介面
 * Created by user on 2016/10/15.
 *
 */
    public class MyAdapter extends BaseAdapter{

        private LayoutInflater myInflater;     //TODO    這三小import
        private List<Restaurant> restaurants;

        public MyAdapter(Context context, List<Restaurant> restaurant){
            myInflater = LayoutInflater.from(context);
            this.restaurants = restaurant;
        }

        /*private view holder class*/
        private class ViewHolder {
            TextView txtTitle;
            TextView txtAddr;
            TextView txtOpenTime;
            TextView txtTel;
            public ViewHolder(TextView txtTitle, TextView txtAddr,TextView txtOpenTime , TextView txtTel){
                this.txtTitle = txtTitle;
                this.txtAddr = txtAddr;
                this.txtOpenTime = txtOpenTime;
                this.txtTel = txtTel;
            }
        }

        @Override
        public int getCount() {    //可以取得到底有多少列的方法
            return restaurants.size();
        }

        @Override
        public Object getItem(int arg0) {  //取得某一列的內容
            return restaurants.get(arg0);
        }

        @Override
        public long getItemId(int position) {   //取得某一列的id

            return restaurants.indexOf(getItem(position));
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {     //最重要的一個方法 要做修改某一列View的內容
            ViewHolder holder = null;
            if(convertView==null){
                convertView = myInflater.inflate(R.layout.listview,null);        //listview
                holder = new ViewHolder(
                        (TextView) convertView.findViewById(R.id.title),
                        (TextView) convertView.findViewById(R.id.addr),
                        (TextView) convertView.findViewById(R.id.openTime),
                        (TextView) convertView.findViewById(R.id.tel)
                );
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            Restaurant restaurant = (Restaurant)getItem(position);
            //0 = restaurant, 1 = title, 2 = nine
            int color_title[] = {Color.BLACK,       Color.BLACK,          Color.BLACK};
            int color_back[] = {0xFFFFED9E,        0xFFFFD306 ,         0xFFFFE366};
            int color_addr[] = {0xFF7B7B7B,        0xFF7B7B7B,          0xFF7B7B7B};
            int color_openTime[] = {0xFF7B7B7B,    0xFF7B7B7B,          0xFF7B7B7B};
            int color_tel[] = { 0xFF7B7B7B,        0xFF7B7B7B,          0xFF7B7B7B};
            int addr_vis[] = {View.VISIBLE,     View.GONE,      View.VISIBLE};
            int openTime_vis[] = {View.VISIBLE,     View.GONE,      View.VISIBLE};
            int tel_vis[] = {View.VISIBLE,     View.GONE,      View.VISIBLE};

            /*色碼表*/
            
            
            /*這裡設定該顯示甚麼，包括名子和地址和背景顏色*/
            int type_num = restaurant.getType();
            holder.txtTitle.setText(restaurant.getName());
            holder.txtTitle.setTextColor(color_title[type_num]);
            holder.txtTitle.setBackgroundColor(color_back[type_num]);
            
            holder.txtAddr.setText(restaurant.getAddr());
            holder.txtAddr.setTextColor(color_addr[type_num]);
            holder.txtAddr.setBackgroundColor(color_back[type_num]);
            holder.txtAddr.setVisibility(addr_vis[type_num]);

            holder.txtOpenTime.setText(restaurant.getOpenTime());
            holder.txtOpenTime.setTextColor(color_openTime[type_num]);
            holder.txtOpenTime.setBackgroundColor(color_back[type_num]);
            holder.txtOpenTime.setVisibility(openTime_vis[type_num]);

            holder.txtTel.setText(restaurant.getTel());
            holder.txtTel.setTextColor(color_tel[type_num]);
            holder.txtTel.setBackgroundColor(color_back[type_num]);
            holder.txtTel.setVisibility(tel_vis[type_num]);


            return convertView;
        }
    }
    
    



