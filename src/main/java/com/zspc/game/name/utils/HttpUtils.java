package com.zspc.game.name.utils;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuansunpengcheng
 * @create 2019-01-21 6:12 PM
 **/
@Component
public class HttpUtils {

    /**
     * 默认超时时间
     */
    private static final Long DEFAULT_TIME_OUT = 45L;


    /**
     * 默认构造器，使用默认超时时间
     */
    public HttpUtils() {
        buildHttpClient(null);
    }

    /**
     * 使用执行超时时间
     *
     * @param timeOut 超时时间
     */
    public HttpUtils(Long timeOut) {
        buildHttpClient(timeOut);
    }


    public static OkHttpClient buildHttpClient(Long timeOut) {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .connectTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut, TimeUnit.SECONDS)
                .build();
    }


    public static Request createGetRequest(String url) {
        return new Request.Builder().url(url).get().build();

    }

}
