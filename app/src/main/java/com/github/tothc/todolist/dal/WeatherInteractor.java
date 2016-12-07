package com.github.tothc.todolist.dal;

import com.github.tothc.todolist.dal.weather.List;
import com.github.tothc.todolist.dal.weather.WeatherData;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.Days;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInteractor {
    WeatherAPI weatherAPI;

    public WeatherInteractor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/forecast/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.weatherAPI = retrofit.create(WeatherAPI.class);
    }

    public void getWeatherData(double latitude, double longitude, DateTime eventDateTime) {
        final int forecastCount = (Days.daysBetween(DateTime.now(), eventDateTime.minusMonths(1)).getDays() == 0) ? 1 : Days.daysBetween(DateTime.now(), eventDateTime.minusMonths(1)).getDays();

        if (forecastCount > 16) {
            // Error
        } else {
            Call<WeatherData> weatherResponse = weatherAPI.getDailyForecast(latitude, longitude, forecastCount, "e08d34962a7fb8c719a3e61ada3959c8");
            weatherResponse.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    EventBus.getDefault().post(processWeatherResponse(response.body(), forecastCount));
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private List processWeatherResponse(WeatherData weatherData, int forecastCount) {
        return weatherData.getList()[forecastCount - 1];
    }

}
