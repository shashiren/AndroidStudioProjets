package com.jiekexueyuan.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNote extends AppCompatActivity implements View.OnClickListener {

    private EditText edTime;
    private EditText edEvent;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        edTime = findViewById(R.id.edt1);
        edEvent = findViewById(R.id.edt2);
        btnAdd = findViewById(R.id.btn1);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
