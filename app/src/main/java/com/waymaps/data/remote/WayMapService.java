package com.waymaps.data.remote;

import com.waymaps.data.model.RemoteTask;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WayMapService {

    @FormUrlEncoded
    @POST("./")
    Call<RemoteTask[]> callSms(@Field(value = "action") String action,@Field(value = "name") String name);



}
