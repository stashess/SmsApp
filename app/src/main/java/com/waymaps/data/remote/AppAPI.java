package com.waymaps.data.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AppAPI {
    private static final String LOG_TAG = AppAPI.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppAPI.class.getSimpleName());

    private static WayMapService wayMapService;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUtil.URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        wayMapService = retrofit.create(WayMapService.class);
    }

}
