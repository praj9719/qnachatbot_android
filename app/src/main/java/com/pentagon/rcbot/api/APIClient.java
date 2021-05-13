package com.pentagon.rcbot.api;

import com.pentagon.rcbot.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;
    private static APIInterface apiInterface;

    private APIClient(){
        buildClient();
    }

    private static void buildClient(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiInterface = retrofit.create(APIInterface.class);
    }

    public static APIInterface getApiInterface(){
        if (retrofit == null) buildClient();
        return apiInterface;
    }

}
