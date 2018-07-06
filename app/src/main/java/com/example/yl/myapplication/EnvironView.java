package com.example.yl.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/7/2.
 */

public class EnvironView extends LinearLayout {
    private LinearLayout square_back;
    private TextView tvSenseName,tvSenseValue;
    public EnvironView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.control_environ,this);
        square_back=(LinearLayout)findViewById(R.id.square_back);
        tvSenseName=(TextView)findViewById(R.id.tv_sense_name);
        tvSenseValue=(TextView)findViewById(R.id.tv_sense_value);
    }
    public  void setSquare_back(int color)
    {
        square_back.setBackgroundColor(color);
    }
    public void setTvSenseName(String text)
    {
        tvSenseName.setText(text);
    }
    public void setTvSenseValue(String text)
    {
        tvSenseValue.setText(text);
    }
}
