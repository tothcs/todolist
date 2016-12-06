package com.github.tothc.todolist.dal;

import com.github.tothc.todolist.dal.weather.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("daily?units=metric")
    Call<WeatherData> getDailyForecast(@Query("lat") double lat, @Query("lon") double lon, @Query("cnt") int cnt, @Query("appid") String appId);

}
