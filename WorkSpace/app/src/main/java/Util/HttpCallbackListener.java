package Util;

/**
 * Created by Administrator on 2018/6/28.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);

}
