package com.example.yl.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        LinearLayout home;
        home = (LinearLayout)findViewById(R.id.mylayout);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(TestActivity.this,"ddd",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
