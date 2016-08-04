package com.example.user.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static com.example.user.myapplication.R.*;
import static com.example.user.myapplication.R.layout.*;

public class Linking extends AppCompatActivity {

    TextView textView,textView2;
    Button button,button2;
    ListView listView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        listView = (ListView) findViewById(id.listView);
        textView = (TextView) findViewById(id.textView);
        textView2 = (TextView) findViewById(id.textView2);
        button = (Button) findViewById(id.button);
        button2 = (Button) findViewById(id.button2);
        editText = (EditText) findViewById(id.editText);

        SetupPairedDevices(); //setup bluetooth 配對過的設備
        SetOnButtonClick(); //setup on buttonClick
    }
    //onCreate end





    BluetoothAdapter bluetoothAdapter; //建立藍牙設備
    ArrayList<BluetoothDevice> pairedDeviceArrayList; //new bluetooth arraylist來存放BLE的設備資料
    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter; //new 藍牙設備容器
    public void SetupPairedDevices() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//取得藍牙的初始Adapter
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices(); //取得配對過的資料
        if (pairedDevices.size() > 0) { //如果配對的資料大於0
            pairedDeviceArrayList = new ArrayList<BluetoothDevice>(); //new bluetooth arraylist來存放BLE的設備資料
            for (BluetoothDevice device : pairedDevices) {
                pairedDeviceArrayList.add(device); //在pairedDeviceArrayList增加資料
            }
            //建立可以對應listview的容器，並且將pairedDeviceArrayList的資料導入
            pairedDeviceAdapter = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1, pairedDeviceArrayList);
            listView.setAdapter(pairedDeviceAdapter);//設定導入listview顯示畫面
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //設定當觸發listview時
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);//取得選取position項目的藍牙設備資料
                    textView2.setText("start ThreadConnectBTdevice");//設定textview2的文字
                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device); //設定藍牙連線的執行緒
                    myThreadConnectBTdevice.start(); //啟動此執行緒
                }
            });
        }
    }

    ThreadConnectBTdevice myThreadConnectBTdevice;
    public class ThreadConnectBTdevice extends Thread {

        public BluetoothSocket bluetoothSocket = null; //建立bluetooth通訊
        public final BluetoothDevice bluetoothDevice;  //建立藍牙設備

        public ThreadConnectBTdevice(BluetoothDevice device) { //初始化建構子
            bluetoothDevice = device; //將傳進來的藍牙設置放入此類別的設置
            try {
                //建構一個溝通的socket,利用UUID
                bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));//利用UUID建立通訊鏈接
                textView2.setText("bluetoothSocket: \n" + bluetoothSocket); //更改textview的文字
            } catch (IOException e) { //如果有出錯,例外處理
                e.printStackTrace();
            }
        }


        @Override
        public void run() { //當ThreadConnectBTdevice被觸發與執行後
            boolean success = false;//假設藍牙連線失敗
            //     BluetoothSocket bluetoothSocket = null;
            try {
                bluetoothSocket.connect();//藍牙連線
                success = true;//狀態改成功
            } catch (IOException e) {//有狀況發現
                e.printStackTrace();
                try {
                    bluetoothSocket.close();//關閉藍牙通訊
                } catch (IOException e1) {//例外處理
                    e1.printStackTrace();
                }
            }


            if (success) { //connect successful
                final String msgconnected = "connect successful:\n" + "BluetoothSocket: " + bluetoothSocket + "\n"
                        + "BluetoothDevice: " + bluetoothDevice;
                runOnUiThread(new Runnable() { //執行UI的執行緒更程式畫面, runnable被執行的程式
                    public void run() {
                        //例外拋出訊息，第一個取得activity權限,第二個參數為要傳送的資料，第三個參數是時間長短
                        Toast.makeText(Linking.this, msgconnected, Toast.LENGTH_SHORT).show();
                    }
                });
                myThreadConnected = new ThreadConnected(bluetoothSocket);//運用執行緒啟動藍牙讀與接受訊息
                myThreadConnected.start();//執行
            } else {
                runOnUiThread(new Runnable() {//執行UI的執行緒更程式畫面, runnable被執行的程式
                    public void run() {
                        //例外拋出訊息，第一個取得activity權限,第二個參數為要傳送的資料，第三個參數是時間長短
                        Toast.makeText(Linking.this, "something wrong bluetoothSocket.connect(): ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    ThreadConnected myThreadConnected;
    public class ThreadConnected extends Thread {
        public final BluetoothSocket connectedBluetoothSocket;//new bluetooth socket
        public final InputStream connectedInputStream;//new read Socket
        public final OutputStream connectedOutputStream;//new send Socket

        public ThreadConnected(BluetoothSocket socket) {//constructor
            connectedBluetoothSocket = socket;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = socket.getInputStream(); //read input
                out = socket.getOutputStream(); //read output
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectedInputStream = in;//set input
            connectedOutputStream = out;//set output
        }


        @Override
        public void run() {
            byte[] buffer = new byte[1024];//給接收String的空間
            int bytes;//給接收String的byte
            while (true) {
                try {
                    bytes = connectedInputStream.read(buffer);//如果input 讀到資料
                    String strReceived = new String(buffer, 0, bytes);//放入strReceived
                    final String msgReceived = String.valueOf(bytes) + " bytes received: " + strReceived;
                    runOnUiThread(new Runnable() { ////執行UI的執行緒更程式畫面, runnable被執行的程式
                        @Override
                        public void run() {
                            textView.setText(msgReceived);//設定textview文字
                        }
                    });
                } catch (IOException e) {//例外處理
                    e.printStackTrace();
                }
            }
        }


        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);//寫入output stream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void SetOnButtonClick() {
        button.setOnClickListener(new View.OnClickListener() {//設定當觸發後
            @Override
            public void onClick(View v) {//override
                if(myThreadConnected!=null){ //當myThreadConnected不等於null
                    byte[] bytesToSend = editText.getText().toString().getBytes();//從edittext取得傳送訊息的byte
                    myThreadConnected.write(bytesToSend);//執行緒output訊息
                    editText.setText("");//clear edittext
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {//設定當觸發後
            @Override
            public void onClick(View v) {//override
                if(myThreadConnected!=null){ //當myThreadConnected不等於null
                    byte[] bytesToSend = "B".getBytes();//傳送訊息的byte
                    myThreadConnected.write(bytesToSend);//執行緒output訊息
                }
            }
        });
    }



}
