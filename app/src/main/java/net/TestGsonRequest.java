package net;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import bean.WeatherInfo;
import model.Weather;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/12 0012
 */

public class TestGsonRequest {
    private static final String TAG = "TestGsonRequest";

    public static void testGsonRequest(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        CustomGsonRequest<Weather> gsonRequest = new CustomGsonRequest<>(
                "http://www.weather.com.cn/data/sk/101010100.html",
                Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {
                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                        Log.d(TAG, "city is " + weatherInfo.getCity());
                        Log.d(TAG, "temp is " + weatherInfo.getTemp());
                        Log.d(TAG, "time is " + weatherInfo.getTime());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
                });
        requestQueue.add(gsonRequest);
    }
}
