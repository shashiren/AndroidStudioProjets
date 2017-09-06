package com.example.zhouguolong.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.zhouguolong.myapplication.GetNumber.lists;

public class MainActivity extends AppCompatActivity {
    public Button btn1;
    public Button button1;
    public Button button2;
    private ListView lv;
    private MyAdapter adapter;
    public TextView tv1;
    public String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermisson();
//        GetNumber.getNumber(this);
        lv = (ListView) findViewById(R.id.lv);
        btn1 = (Button) findViewById(R.id.btn1);
//        button1 = (Button) findViewById(R.id.button1);
        adapter = new MyAdapter(lists, this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                number = lists.get(position).getNumber();
                showDialog();

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("添加");

            }
        });
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("打电话");
//                callphone(PhoneInfo.class.getName());
//            }
//        });





//        System.out.println("hello world!");
//        btn1 = (Button) findViewById(R.id.button);
//        tv1 = (TextView) findViewById(R.id.tv1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              tv1.setText("你好MacBook第一个程序");
//            }
//        });
    }
    public void showDialog(){
        View view = getLayoutInflater().inflate(R.layout.call_phone,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.contacts);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(getString(R.string.call));
                callphone(number);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(number);
            }
        });

    };


    public void addNumber(){
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getContext().getContentResolver();
    }

    private void callphone(String phoneNumber){

        Uri uri = Uri.parse("tel:"+phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL,uri);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        }


    }
    private void sendMessage(String phoneNumber){

        Uri uri = Uri.parse("sms:"+phoneNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        }

    }

    public void getPermisson(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                GetNumber.getNumber(this);
            }else {
//                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.});
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("注意!");
                builder1.setMessage("请设置输入权限");
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder1.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setting();
                    }
                });
                builder1.show();
            }
        }else {
            GetNumber.getNumber(this);
        }
    }

    public void setting (){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);

    }
}
