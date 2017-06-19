package net;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import Utils.LogUtil;

/**
 * @author dingjinwen
 * @description 请求网络图片用法一
 * 用法和StringRequest相同
 * @date 2017/6/12 0012
 */

public class TestImageRequest {
    private static final String TAG = "TestImageRequest";

    public static void testImageRequestForGet(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        //第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，指定成0的话就表示不管图片有多大，都不会进行压缩。
        //第五个参数用于指定图片的颜色属性，Bitmap.Config下的几个常量都可以在这里使用，其中ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，而RGB_565则表示每个图片像素占据2个字节大小。
        //第六个参数是图片请求失败的回调。
        ImageRequest imageRequest = new ImageRequest(
                "http://developer.android.com/images/home/aw_dac.png",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        LogUtil.d(TAG, response.toString());
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, error.getMessage());
            }
        });
        queue.add(imageRequest);
    }
}
