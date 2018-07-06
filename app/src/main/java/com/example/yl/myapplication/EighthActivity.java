package com.example.yl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Util.HttpCallbackListener;
import Util.HttpUtil;
import okhttp3.FormBody;

public class EighthActivity extends Activity implements View.OnClickListener{
    private Button buttonRecharge;
    private Button buttonQuery;
    private int Index;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth);
        buttonRecharge = (Button) findViewById(R.id.btn_recharge);
        buttonQuery = (Button) findViewById(R.id.btn_query);
        buttonQuery.setOnClickListener(this);
        buttonRecharge.setOnClickListener(this);
        initSpinner((Spinner)findViewById(R.id.spinner));
        textView = (TextView) findViewById(R.id.textView);
        queryBalace();
    }
    public void initSpinner(final Spinner spinner){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Index= position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void queryBalace(){
        HttpUtil httpUtil = new HttpUtil();
        final FormBody formBody = new FormBody.Builder()
                .add("UserName", "Z0004")
                .add("CarId",Index+1+"")
                .build();
        httpUtil.sendOkhttpRequest(new HttpCallbackListener() {
            public String balance;
            public JSONObject jsonObject;

            @Override
            public void onFinish(String response) {
//                    Log.d("TAG", "onFinish: re"+response);
                try {
                    jsonObject = new JSONObject(response);
                    balance = jsonObject.get("Balance").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("账户余额:"+balance);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        }, formBody, "GetCarAccountBalance");
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_query){
             queryBalace();
        }
        else if(v.getId()==R.id.btn_recharge){
            HttpUtil httpUtil = new HttpUtil();
            final FormBody formBody = new FormBody.Builder()
                    .add("UserName", "Z0004")
                    .add("CarId",Index+1+"")
                    .add("Money",((EditText)findViewById(R.id.edittext)).getText().toString())
                    .build();
            httpUtil.sendOkhttpRequest(new HttpCallbackListener() {

                @Override
                public void onFinish(String response) {
                    Log.d("TAG", "onFinish: re"+response);
                    String balance = null;
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response);
                        Log.d("TAG", "onFinish: "+response);
                        String re = jsonObject.get("RESULT").toString();
                        if("S".equals(re)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    queryBalace();
                                    Toast.makeText(EighthActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    textView.setText(balance);
                }

                @Override
                public void onError(Exception e) {

                }
            }, formBody, "SetCarAccountRecharge");
        }
    }

}
