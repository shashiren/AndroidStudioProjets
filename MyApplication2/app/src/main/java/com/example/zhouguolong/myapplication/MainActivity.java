package com.example.zhouguolong.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public Button btn1;
    public Button button1;
    public Button button2;
    private ListView lv;
    private MyAdapter adapter;
    public TextView tv1;
    public String number;
    private List<PhoneInfo> contacts = new ArrayList<PhoneInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermisson();
//        ContactManager.getContact(this,);
        lv = (ListView) findViewById(R.id.lv);
        btn1 = (Button) findViewById(R.id.btn1);
//        button1 = (Button) findViewById(R.id.button1);
        adapter = new MyAdapter(this,contacts);
        lv.setAdapter(adapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
                setContactData();
                System.out.println("添加");

                }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhoneInfo contact = (PhoneInfo) adapter.getItem(position);
//                number = contacts.get(position).getNumber();
                showCallDialog(contact);
                setContactData();

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                PhoneInfo contact = (PhoneInfo) adapter.getItem(position);
                ContactManager.deleteContact(MainActivity.this,contact);

                setContactData();
                return true;
            }
        });

        setContactData();

    }
    private void setContactData(){
        List<PhoneInfo> contactData = ContactManager.getContact(this);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.notifyDataSetChanged();
    }
    public void showAddDialog(){
        View view = getLayoutInflater().inflate(R.layout.addcontact,null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
        new AlertDialog.Builder(this)
                .setTitle("添加联系人")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhoneInfo contact = new PhoneInfo();
                        contact.setName(et_name.getText()+"");
                        contact.setNumber(et_phone.getText()+"");

                        ContactManager.addContact(MainActivity.this,contact);
                        setContactData();

                    }
                })
                .setNegativeButton("取消",null)
                .show();

    }
    public void showUpdateDialog(final PhoneInfo oldContact){
        View view = getLayoutInflater().inflate(R.layout.addcontact,null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        et_name.setText(oldContact.getName());
        et_phone.setText(oldContact.getNumber());
        new AlertDialog.Builder(this)
                .setTitle("修改联系人")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhoneInfo contact = new PhoneInfo();
                        contact.setRawContactId(oldContact.getRawContactId());
                        contact.setName(et_name.getText()+"");
                        contact.setNumber(et_phone.getText()+"");

                        ContactManager.updateContct(MainActivity.this,contact);
                        setContactData();

                    }
                })
                .setNegativeButton("取消",null)
                .show();

    }


    public void showCallDialog(final PhoneInfo contact){
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
                callphone(contact);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(contact);
            }
        });

    };


    private void callphone(PhoneInfo phoneNumber){

        Uri uri = Uri.parse("tel:"+phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL,uri);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        }


    }
    private void sendMessage(PhoneInfo phoneNumber){

        Uri uri = Uri.parse("sms:"+phoneNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        }

    }

    public void getPermisson(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
//                ContactManager.getNumber(this);
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
//            ContactManager.getNumber(this);
        }
    }

    public void setting (){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);

    }
}
