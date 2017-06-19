package net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Utils.LogUtil;

/**
 * @author dingjinwen
 * @description 文本数据，如html等
 * 1.创建一个RequestQueue对象
 * 2.创建一个StringRequest对象
 * 3.将StringRequest对象加入RequestQueue里面
 * @date 2017/6/12 0012
 */
public class TestStringRequest {
    private static final String TAG = "TestStringRequest";

    public static void testStringRequestForGet(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        //Get请求的方式，默认就是GET请求方式
        StringRequest stringRequest = new StringRequest("https://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.d(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    public static void testStringRequestForPost(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        //Post请求的方式
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.d(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, error.getMessage());
            }
        }) {
            //重写getParams()方法，在这里设置POST参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };
        queue.add(stringRequest);
    }
}
