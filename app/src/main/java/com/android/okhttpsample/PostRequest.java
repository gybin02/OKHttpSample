package com.android.okhttpsample;

import android.util.Log;

import com.android.okhttpsample.http.AesRequestInterceptor;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ali on 4/10/17.
 */

public class PostRequest {
    private static final String TAG = "PostRequest";

    public static void executePostRequest() {
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

//        OkHttpClient client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new GzipRequestInterceptor())
                .addInterceptor(new AesRequestInterceptor())
                .build();
//        client.interceptors().add(new AesRequestInterceptor());

        String url = "https://test-diaries.seeyouyima.com/v2/test";
//         "?myuid=167266757&utdid=Whzd1yQcpzgDAMoGyEWh189W";
        String json = "{\"mode\" : \"test\"}"; // Json Content ...

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
//                .header("Content-Encoding","gzip")
                .url(url)
                .post(body)
                .build();

//        source: SYCalendarViewController
//        lang: zh
//        imy-rp: 1
//        user-agent: Seeyou/6.3 (iPhone; iOS 11.1; Scale/2.00)
//        content-signature: signature=8B2F32B3A4E00F6BF3D67EC11EBD3F0B91440A56;signer=1;timestamp=Wed, 29 Nov 2017 06:37:24 GMT
//        myclient: 0120630000000000
//        myuid: 167266757
//        authorization-virtual: VDS 7.ngTntwZpzxHji9u_cc9V-M43YKxzc3MFSu4teIqPxxY
//        statinfo: eyJidWlsZHYiOiI2LjMiLCJvcyI6IjEiLCJ1aWQiOiIxNjcyNjY3NTciLCJ1YSI6Ing4Nl82NCIsImlkZmEiOiJCMDM4RTJFMC04NzVDLTRFNEEtQkI3Qy1EREI3NDVENDBFQ0YiLCJvdCI6IiIsImRuYSI6IjlhMDI0NzliODU3NmE0ZDk0Mjg1ZTJmODM1MTdjZjg0OWY3NGZhZTQ0NWZhNDE2NWE4YjE1MmM0NzIwY2Q1YWIiLCJvc3ZlcnNpb24iOiIxMS4xIiwib3BlbnVkaWQiOiJvaWQtM2MwYTAxZWQ1OTI3MWIwMTAyYmI3YmJiNjIwNTkyMmRjNDAyMmUzNyIsImFwbiI6IjQifQ==
//                deviceid: B038E2E0-875C-4E4A-BB7C-DDB745D40ECF
//        authorization: XDS 7.ngTntwZpzxHji9u_cc9V-M43YKxzc3MFSu4teIqPxxY
//        scale: 2.0
//        accept-language: en;q=1
//        themeid: 0
//        content-type: application/x-www-form-urlencoded
//        accept: */*
//mode: 0
//utdid: Whzd1yQcpzgDAMoGyEWh189W
//elder: b0iu64b738J5chBhyTi1Bw==

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // manage failure
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Manage response
                Log.i(TAG, response.body().string());
            }
        });
    }
}
