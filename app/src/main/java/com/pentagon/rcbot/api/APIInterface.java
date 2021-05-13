package com.pentagon.rcbot.api;

import com.pentagon.rcbot.api.qcb.QCBResult;
import com.pentagon.rcbot.api.reverse.RequestBody;
import com.pentagon.rcbot.api.reverse.ReverseResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("reverse")
    Call<ReverseResult> reverseString(@Body RequestBody body);

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("{bot_name}")
    Call<QCBResult> chat(@Path("bot_name") String bot_name, @Body com.pentagon.rcbot.api.qcb.RequestBody body);

}
