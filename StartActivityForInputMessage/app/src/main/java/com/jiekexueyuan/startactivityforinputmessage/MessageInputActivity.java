package com.jiekexueyuan.startactivityforinputmessage;

/**
 * Created by stan on 2017/11/9.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MessageInputActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_input);
        mEditMessage = (EditText) findViewById(R.id.edit1);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.edit1).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MessageInputActivity.this,MainActivity.class);
//        Toast tipsToast = Toast.makeText(MessageInputActivity.this,"你取消了许愿操作",Toast.LENGTH_LONG);
//        tipsToast.setGravity(Gravity.CENTER,0,0);

        switch (view.getId()){
            case R.id.btn1:
                String str1 = getString(R.string.str1);
                if("".equals(mEditMessage.getText().toString())) {
                    String str = getString(R.string.str);
                    Toast.makeText(MessageInputActivity.this, str, Toast.LENGTH_SHORT).show();
//                    mEditMessage.setText(str);

                }else {
                    i.putExtra("data",str1+mEditMessage.getText().toString());

                    startActivity(i);
                    finish();
                }

                break;
            case R.id.btn2:
                startActivity(i);
                Toast.makeText(MessageInputActivity.this, "你取消了许愿操作", Toast.LENGTH_SHORT).show();
                finish();
//                tipsToast.show();
                break;
        };
    }
}
