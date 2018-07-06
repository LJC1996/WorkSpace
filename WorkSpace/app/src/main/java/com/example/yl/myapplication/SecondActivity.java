package com.example.yl.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Util.HttpCallbackListener;
import Util.HttpUtil;
import okhttp3.FormBody;

public class SecondActivity extends Activity implements HttpCallbackListener ,AdapterView.OnItemClickListener {
    ListView lv;
    public static String queryCycleValues[] = {"temperature", "humidity", "co2", "LightIntensity", "pm25"};
    public static String queryCycleNames[] = {"空气温度", "空气湿度", "二氧化碳", "光照强度", "PM2.5"};
    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter listAdapter = null;
    private HttpUtil httpUtil;
    private HttpCallbackListener that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initListView();
        initData();
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final FormBody formBody = new FormBody.Builder()
                            .add("UserName", "Z0004")
                            .build();
                    httpUtil = new HttpUtil();
                    httpUtil.sendOkhttpRequest(that, formBody, "GetAllSense");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void initListView() {
        lv = (ListView) findViewById(R.id.lsv_list);
//        listItem.clear();
        listAdapter = new SimpleAdapter(this,
                listItem,
                R.layout.item_second_layout,
                new String[]{"name", "data"},
                new int[]{R.id.item1, R.id.item2});
        lv.setAdapter(listAdapter);
//        mListView.setAdapter(simpleAdapter);
        lv.setOnItemClickListener(this);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFinish(String response) {
        listItem.clear();
        JSONObject jsonObject = null;
        String str = null;
        try {
            jsonObject = new JSONObject(response);
            String result = jsonObject.get("RESULT").toString();
            if (!"S".equals(result)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SecondActivity.this, "数据获取失败！ 请重新尝试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<queryCycleValues.length;i++){
            try {
                String result = jsonObject.get("RESULT").toString();
                HashMap<String, String> map1 = new HashMap<String, String>();
                map1.put("name", queryCycleNames[i]);
                map1.put("data", (jsonObject.get(queryCycleValues[i])).toString());
                listItem.add(map1);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(FifthActivity.this, queryCycleName,Toast.LENGTH_SHORT).show();
                        listAdapter.notifyDataSetChanged();
                        return;
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("dataName",queryCycleNames[position]);
        intent.putExtra("dataValuse",queryCycleValues[position]);
        this.startActivity(intent);

//        Log.d("TAG", "onItemClick: "+position);
    }
}
