package com.xmgl.kan.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xmgl.kan.BuildConfig;
import com.xmgl.kan.network.entity.BaseEntity;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;


public class Network {

    private Apis api;

    private Network() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(getLogInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(Apis.class);
    }


    private static class SingletonHolder {
        private static final Network INSTANCE = new Network();
    }

    private static Interceptor getLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    //获取单例
    public static Apis getApis() {
        return SingletonHolder.INSTANCE.api;
    }

    // network available

    public static boolean isAvailable(Context ctx) {
        Context appContext = ctx.getApplicationContext();
        ConnectivityManager mgr = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo networkInfo = mgr.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

    // 网络返回统一处理
    public static <T> Observable.Transformer<BaseEntity<T>, T> check() {
        return httpResultObservable -> httpResultObservable.flatMap((Func1<BaseEntity<T>, Observable<T>>) result -> {

            if (result == null || result.getCode() != 1) {
                return Observable.error(new Throwable("数据获取失败"));
            } else {
                return Observable.just(result.getBody());
            }
        });
    }

}
