package com.kwaijian.facility.UI.BaseClass.Views.Browser;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;


import com.kwaijian.facility.OldSource.tools.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/9/20.
 * 用于图片浏览器缓存图片到内存
 */

public class HTImageCache {

    private List<RemovedListener> listeners = new ArrayList<>();
    private LruCache<String, Bitmap> mCache;

    public HTImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        mCache = new LruCache<String, Bitmap>(maxMemory / 6) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024 / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
//                log("entryRemoved size:" + key);
                for (RemovedListener l : listeners) {
                    l.onRemoved(key);
                }
            }
        };
    }

    public void put(String key, Bitmap bitmap) {
        mCache.put(key, bitmap);
//        log("width:" + bitmap.getWidth() + ",height:" + bitmap.getHeight());
//        log("cache size:" + mCache.size() + "/" + mCache.maxSize() + ", count:" + mCache.putCount() + ",misCount:" + mCache.missCount());
    }

    public Bitmap get(String key) {
        if (key != null) {
            Bitmap b = mCache.get(key);
            if (b != null && !b.isRecycled()) {
                return b;
            }
        }
        return null;
    }

    public void log(String log) {
        LogUtils.d(log);
    }

    public void addRemovedListener(RemovedListener l) {
        listeners.add(l);
    }

    public void removeRemovedListener(RemovedListener l) {
        listeners.remove(l);
    }

    interface RemovedListener {
        void onRemoved(String key);
    }


}
