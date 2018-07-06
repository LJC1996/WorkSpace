package com.example.yl.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Util.HttpCallbackListener;
import Util.HttpUtil;
import okhttp3.FormBody;

public class ThirdActivity extends Activity implements HttpCallbackListener{
    HttpCallbackListener that=this;
    HttpUtil httpUtil=null;
    String dataValuse="";
    private boolean flag=false;
    private int index=0;
    LineChart mLineChart;
    List<Entry> entries = new ArrayList<>();
    private Entry entry=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mLineChart = (LineChart) findViewById(R.id.lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        dataValuse = getIntent().getStringExtra("dataValuse");
        String dataName = getIntent().getStringExtra("dataName");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(dataName);
        initLineChart();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=true;
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(flag)break;
                    final FormBody formBody = new FormBody.Builder()
                            .add("UserName", "Z0004")
                            .add("SenseName",dataValuse)
                            .build();
                    httpUtil = new HttpUtil();
                    httpUtil.sendOkhttpRequest(that, formBody, "GetSenseByName");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void initLineChart() {

//        //设置数据
//        for (int i = 0; i < 100; i++) {
        entry=new Entry(index, (float) 0);
            if(entry!=null)
            entries.add(entry);
//            entries.add(entry）;//new Entry(i, (float) (Math.random()) * 80));
//        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "数值大小");
        lineDataSet.setColor(Color.GREEN);
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
    }

    @Override
    public void onFinish(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int data = jsonObject.getInt(dataValuse);

            Log.d("TAG", "onFinish: "+data);

                    entry=new Entry(index, (float) (data));
                    index++;
            entries.add(entry);
            LineDataSet lineDataSet = new LineDataSet(entries, "数值大小");
            lineDataSet.setColor(Color.GREEN);
            LineData data1;
            data1 = new LineData(lineDataSet);
            mLineChart.setData(data1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Exception e) {

    }
}
