package com.example.user.myapplication.test;



import android.app.Activity;

import org.junit.Test;

/**
 * Created by user on 2016/10/1.
 */
public class Connect extends Activity {/*



    String showUri = "http://120.105.132.39/app_link/loadingdata.php";

    com.android.volley.RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {  //將上一個頁面的資訊丟過來
        super.onCreate(savedInstanceState);  //我接 <3
        setContentView(activity_blink);

        requestQueue = Volley.newRequestQueue(getApplicationContext());




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest


                (DownloadManager.Request.Method.POST,showUri, new Response.Listener<JSONObject>() {


                    @Override


                    public void onResponse(JSONObject response) {


                        System.out.println(response.toString());


                        try {


                            JSONArray data = response.getJSONArray("data");


                            //這邊要和上面json的名稱一樣


                            //下邊是把全部資料都印出來


                            for (int i = 0; i < data.length(); i++) {


                                JSONObject jasondata = data.getJSONObject(i);


                                String account = jasondata.getString("name");


                                String pwd = jasondata.getString("works");


                                txt.append(account + " " + pwd + " " + " \n");


                                //txt是textview


                            }


                            txt.append("===\n");//把資料放到textview顯示出來


                        } catch (JSONException e) {


                            e.printStackTrace();


                        }


                    }


                }, new Response.ErrorListener() {


                    @Override


                    public void onErrorResponse(VolleyError error) {


                        System.out.append(error.getMessage());





                    }


                });


        requestQueue.add(jsonObjectRequest);



    }

*/

}