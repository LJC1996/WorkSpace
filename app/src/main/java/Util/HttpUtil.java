package Util;

//import android.util.Log;
//import android.view.View;
//
//import com.example.yl.myapplication.FifthActivity;
//
//import java.io.IOException;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/27.
 */

public class HttpUtil {
    public void sendOkhttpRequest(final HttpCallbackListener listener, final FormBody formBody, final String httpUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://efsports.fengaiyi.com/appWeb/public/"+httpUrl)//GetSenseByName")
                            .post(formBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();
                    Log.d("123.aaa",res);
                    listener.onFinish(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}


