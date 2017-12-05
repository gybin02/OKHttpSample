package com.android.okhttpsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.okhttpsample.http.AESOperator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);

    }

    // Implement the OnClickListener callback
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                System.out.println("Test:  click button1");
                SynchronousGet.executeSynchronousGet();
                break;

            case R.id.button2:
                System.out.println("Test:  click button2");
                AsynchronousGet.executeAsynchronousGet();
                break;

            case R.id.button3:
                System.out.println("Test:  click button3");
                PostRequest.executePostRequest();
                break;
            case R.id.button4:
                test();
                break;
            default:
                break;
        }
    }


    public void test() {
        String content = "啊啊坎大哈的卡扩大2342jndsfsx";

        try {
            AESOperator aes =  AESOperator.getInstance();
        
//            String temp="7prXtbnTSDKCMUEXJCVXjpvx/5crbsLTV6TCHkSuYEg=";
//            Log.e(TAG, aes.decrypt(temp));
            

            //加密
            Log.e(TAG, "加密前：" + content);
            String encrypt = aes.encrypt(content);
            Log.e(TAG, "加密后：" + new String(encrypt));
            //解密
            String decrypt = aes.decrypt(encrypt);
            Log.e(TAG, "解密后：" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
