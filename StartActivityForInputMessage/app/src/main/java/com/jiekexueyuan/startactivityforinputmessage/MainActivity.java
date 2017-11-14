package com.jiekexueyuan.startactivityforinputmessage;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mTvObtainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        mTvObtainContent = (TextView) findViewById(R.id.tv1);
        mTvObtainContent.setText(i.getStringExtra("data"));

        findViewById(R.id.btn1).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent messageInput = new Intent(MainActivity.this,MessageInputActivity.class);
        startActivity(messageInput);
        finish();
    }
}
