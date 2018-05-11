package com.itant.autoclick.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpClientManager {
    private static final String OK_BASE_URL = "http://www.dgli.net/";

    private static final String TEST_BASE_URL = "http://www.dgli.net:8888/";
    private static String BASE_URL = TEST_BASE_URL;

    private static OkHttpClientManager mInstance;
    private Handler mDelivery;

    private OkHttpClientManager(Context context) {
        BASE_URL = TEST_BASE_URL;
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static void init(Context context){
        if (mInstance==null){
            synchronized (OkHttpClientManager.class){
                if (mInstance==null){
                    mInstance =  new OkHttpClientManager(context);
                }
            }
        }
    }
    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("OkHttpClientManager must be init meold");
        }
        return mInstance;
    }


    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }
    /**
     * 批量上传文件操作
     *
     * @param url
     * @param map
     * @param files
     * @param callback
     */
    public static void _postContentAndFiles(String url, final Map<String, String> map, List<File> files, final OkHttpClientManager
            .ResultCallback callback) {
        /* form的分割线,自己定义 */
        String boundary = "xx--------------------------------------------------------------xx";
        MultipartBody.Builder builder = new MultipartBody.Builder(boundary);
        builder.setType(MultipartBody.FORM);
        if (map != null && map.size() > 0) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String value = map.get(key);
                builder.addFormDataPart(key, value);
            }
        }
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                okhttp3.RequestBody fileBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/jpeg"), files.get(i));
                builder.addFormDataPart("file", files.get(i).getName(), fileBody);
            }
        }
        MultipartBody mBody = builder.build();
        Request request = new Request.Builder().url(BASE_URL + url).post(mBody).build();
        deliveryResult(callback, request);
        LogUtils.logd("url" + BASE_URL + url + "\n map" + map.toString() + "file" + files.toString());
    }
    /**
     * 返回结果基类
     *
     */
    public static abstract class ResultCallback {

        public abstract void onError(Request request, Exception e);

        public abstract void onStart();

        public abstract void onResponse(String response);

    }


    public static class StringResultCallBack extends ResultCallback{

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onResponse(String response) {

        }
    }
    /**
     * 处理请求错误回调
     *
     * @param request
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) callback.onError(request, e);
            }
        });
    }
    /**
     * 处理请求成功回调
     *
     * @param object
     * @param callback
     */
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object.toString());
                }
            }
        });
    }
    /**
     * 处理请求结果
     *
     * @param callback
     * @param request
     */
    private static void deliveryResult(final ResultCallback callback, final Request request) {
        if (callback != null) {
            callback.onStart();
        }
        getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string().trim();
                    getInstance().sendSuccessResultCallback(string, callback);

                    LogUtils.logd(request.url().toString() + "\n" + string);

                } catch (IOException | com.google.gson.JsonParseException e) {
                    getInstance().sendFailedStringCallback(response.request(), e, callback);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getInstance().sendFailedStringCallback(request, e, callback);
            }
        });
    }

}
