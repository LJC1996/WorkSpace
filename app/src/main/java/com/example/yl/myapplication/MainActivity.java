package com.example.yl.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button button[];
    private Integer[] buttonId;
    private ArrayList list_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();
        initbtn();
        initEvent();
    }

    private void initEvent() {
        for(int i=0;i<button.length;i++){
            button[i].setOnClickListener(this);
        }
    }

    private void initId() {
        buttonId= new Integer[]{
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9
        };
    }
    private void initbtn() {
        button = new Button[ buttonId.length];
        for(int i=0;i<buttonId.length;i++){
            button[i]=(Button) findViewById(buttonId[i]);
        }
    }

    private void initActvity() {
        list_activity= new ArrayList<AppCompatActivity>();
        list_activity.add(0,TestActivity.class);
        list_activity.add(1,SecondActivity.class);
        list_activity.add(2,SecondActivity.class);
        list_activity.add(3,FourthActivity.class);
        list_activity.add(4,FifthActivity.class);
        list_activity.add(5,FifthActivity.class);
        list_activity.add(6,FifthActivity.class);
        list_activity.add(7,EighthActivity.class);
        list_activity.add(8,FifthActivity.class);
    }

    @Override
    public void onClick(View v) {
        initActvity();
        for(int i=0;i<button.length;i++){
            if(buttonId[i]== v.getId()){
                Intent intent = new Intent(MainActivity.this, (Class<?>) list_activity.get(i));
                startActivity(intent);
            }
        }
    }

}
