package com.example.yl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

public class FifthActivity extends Activity implements HttpCallbackListener{
    Thread thread=null;
    ListView lv;
    ArrayList<HashMap<String, String>> listItem= new ArrayList<HashMap<String,String>>();
    private SimpleAdapter listAdapter=null;
    public static int queryCycleIndex=0;
    public static int TimeValuesIndex=0;
    public static String queryCycleValues[]={"temperature","humidity","co2","LightIntensity","pm25"};
    public static String queryCycleNames[]={"空气温度","空气湿度","二氧化碳","光照强度","PM2.5"};
    public static int TimeValues[]={3,5};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        final Spinner spinner_sensortype = (Spinner) findViewById(R.id.sensortype);
        final Spinner query_cycle = (Spinner) findViewById(R.id.query_cycle);
        Button btn_select = (Button) findViewById(R.id.btn_select);
        final FifthActivity that = this;
//        初始化LsstView
        initListView();
//        获取下拉列表的值
        initSpinner(spinner_sensortype,1);
        initSpinner(query_cycle,2);
        //点击按钮应做的处理
        initButton(btn_select);
    }

    public void initButton(Button btn){
        final FifthActivity that = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HttpUtil httpUtil = new HttpUtil();
                boolean flag=true;
                if(thread!=null){
                    flag=false;
                }
                final boolean finalFlag = flag;
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if(finalFlag ==false)break;
                            try {
                                final FormBody formBody = new FormBody.Builder()
                                        .add("UserName", "Z0004")
                                        .add("SenseName", String.valueOf(queryCycleValues[queryCycleIndex]))
                                        .build();
                                httpUtil.sendOkhttpRequest(that, formBody, "GetSenseByName");
                                Thread.sleep(TimeValues[TimeValuesIndex] * 1000);
                            }  catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                thread.start();
                flag=true;
            }
        });
    }
    public void initSpinner(final Spinner spinner, final int Intdex){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(Intdex==1)
                    queryCycleIndex= position;
                if(Intdex==2)
                    TimeValuesIndex= position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void initListView()
    {
        lv=(ListView)findViewById(R.id.lsv_history);
        listItem.clear();
        listAdapter=new SimpleAdapter(this,
                listItem,
                R.layout.item_history_layout,
                new String[] {"SenseName","data","ifNormal","time"},
                new int[] {R.id.item1,R.id.item2,R.id.item3,R.id.item4});
        lv.setAdapter(listAdapter);
    }

    @Override
    public void onFinish(final String response) {
        JSONObject jsonObject = null;
        String str=null;
        try {
            String queryCycleValue;
            queryCycleValue = queryCycleValues[queryCycleIndex];
            final String queryCycleName = queryCycleNames[queryCycleIndex];
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            jsonObject = new JSONObject(response);
            String result = jsonObject.get("RESULT").toString();
            HashMap<String,String> map1=new HashMap<String,String>();
            map1.put("SenseName",queryCycleName);
            map1.put("data",(jsonObject.get(queryCycleValue)).toString());
            map1.put("ifNormal","正常");
            map1.put("time",df.format(new Date()).toString());
            listItem.add(0,map1);
            if(!"S".equals(result)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FifthActivity.this, "数据获取失败！ 请重新尝试",Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(FifthActivity.this, queryCycleName,Toast.LENGTH_SHORT).show();
                        listAdapter.notifyDataSetChanged();
                        return;
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onError(Exception e) {

    }
}
