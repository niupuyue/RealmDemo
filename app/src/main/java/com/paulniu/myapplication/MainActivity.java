package com.paulniu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.paulniu.myapplication.callbackdemo.CallbackActivity;
import com.paulniu.myapplication.officedemo.OfficeDemoActivity;
import com.paulniu.myapplication.selfdemo.SelfDemoActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_office_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_office_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OfficeDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_self_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelfDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_callback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallbackActivity.class);
                startActivity(intent);
            }
        });

    }
}
