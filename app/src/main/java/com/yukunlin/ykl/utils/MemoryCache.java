package com.yukunlin.ykl.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2015/11/3.
 */
public class MemoryCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mCache;

    public MemoryCache() {
        mCache = new LruCache<>(100);
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
