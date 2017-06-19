package net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import Utils.LogUtil;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/12 0012
 */

public class TestXMLRequest {
    private static final String TAG = "TestXMLRequest";

    public static void testXMLRequest(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        CustomXMLRequest customXMLRequest = new CustomXMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml", new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                LogUtil.d(TAG, response.toString());
                try {
                    int eventType = response.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if ("city".equals(nodeName)) {
                                    String pName = response.getAttributeValue(0);
                                    LogUtil.d(TAG, "pName is " + pName);
                                }
                                break;
                        }
                        eventType = response.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, error.getMessage());
            }
        });
        queue.add(customXMLRequest);
    }
}
