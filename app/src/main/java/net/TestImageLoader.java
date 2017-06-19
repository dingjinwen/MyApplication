package net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;

/**
 * @author dingjinwen
 * @description 请求网络图片用法二, 比ImageRequest高效，可进行图片缓存，过滤重复链接，避免重复发送请求
 * 1. 创建一个RequestQueue对象。
 * 2. 创建一个ImageLoader对象。
 * 3. 获取一个ImageListener对象。
 * 4. 调用ImageLoader的get()方法加载网络上的图片。
 * @date 2017/6/12 0012
 */

public class TestImageLoader {
    private static final String TAG = "TestImageLoader";

    public static class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }

    public static void testImageLoader(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        //第一个参数是设置图片的控件imageView
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(null, R.drawable.icon_autofeed_nor, R.drawable.icon_autofeed_nor);
        imageLoader.get("http://developer.android.com/images/home/aw_dac.png", listener);
        //如果你想对图片的大小进行限制，也可以使用get()方法的重载，指定图片允许的最大宽度和高度，如下所示：
//        imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",listener, 200, 200);
    }
}
