package net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.R;

/**
 * @author dingjinwen
 * @description 请求网络图片用法三，NetworkImageView是一个自定义控制，它是继承自ImageView的，具备ImageView控件的所有功能，并且在原生的基础之上加入了加载网络图片的功能。NetworkImageView控件的用法要比前两种方式更加简单
 * 1. 创建一个RequestQueue对象。
 * 2. 创建一个ImageLoader对象。
 * 3. 在布局文件中添加一个NetworkImageView控件。
 * 4. 在代码中获取该控件的实例。
 * 5. 设置要加载的图片地址。
 * @date 2017/6/12 0012
 */

public class TestNetWorkImageView {
    private static final String TAG = "TestNetWorkImageView";

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

    public static void testNetWorkImageView(Context context, View view, int id_network_image_view) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(id_network_image_view);
        networkImageView.setDefaultImageResId(R.drawable.icon_autofeed_nor);
        networkImageView.setErrorImageResId(R.drawable.icon_autofeed_nor);
        networkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageLoader);
    }
}
