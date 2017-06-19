package net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import Utils.LogUtil;

/**
 * @author dingjinwen
 * @description json数据
 * 用法和StringRequest相同
 * @date 2017/6/12 0012
 */

public class TestJsonRequest {

    private static final String TAG = "TestJsonRequest";

    public static void testJsonObjectRequest(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static void testJsonArrayRequest(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://m.weather.com.cn/data/101010100.html", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LogUtil.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
}
