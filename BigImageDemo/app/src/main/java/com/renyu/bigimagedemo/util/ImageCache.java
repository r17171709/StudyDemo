package com.renyu.bigimagedemo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ImageCache {
    private static volatile ImageCache instance = null;

    private Set<WeakReference<Bitmap>> reusepool = null;

    private LruCache<String, Bitmap> mLruCache = null;

    public static ImageCache getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageCache.class) {
                if (instance == null) {
                    instance = new ImageCache(context);
                }
            }
        }
        return instance;
    }

    private ImageCache(Context context) {
        init(context.getApplicationContext());
    }

    private void init(Context context) {
        reusepool = Collections.synchronizedSet(new HashSet<WeakReference<Bitmap>>());

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memory = activityManager.getMemoryClass();

        mLruCache = new LruCache<String, Bitmap>(memory * 1024 * 1024 / 8) {
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                if (oldValue.isMutable()) {
                    reusepool.add(new WeakReference(oldValue));
                }
            }

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getAllocationByteCount();
            }
        };
    }

    public void putBitmap2Memory(String key, Bitmap bitmap) {
        mLruCache.put(key, bitmap);
    }

    public Bitmap getBitmapFromMemory(String key) {
        return mLruCache.get(key);
    }

    public void clearMemory() {
        mLruCache.evictAll();
    }

    public Bitmap getReuseable(int w, int h) {
        Iterator<WeakReference<Bitmap>> iterator = reusepool.iterator();
        while (iterator.hasNext()) {
            WeakReference<Bitmap> weakReference = iterator.next();
            Bitmap bitmap = weakReference.get();
            if (bitmap == null) {
                iterator.remove();
            } else {
                // 检查是否有可复用的bitmap
                if (checkInBitmap(w, h, bitmap)) {
                    iterator.remove();
                    return bitmap;
                }
            }
        }
        return null;
    }

    private Boolean checkInBitmap(int w, int h, Bitmap mBitmap) {
        // 假定这边限制的是jpg
        int needUse = w * h * 2;
        // 4.4版本之后，可以使用比自己大的bitmap
        return needUse <= mBitmap.getAllocationByteCount();
    }

    public Bitmap resizeBitmap(InputStream inputStream, Bitmap reuseable) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
//        mOptions.inJustDecodeBounds = true
//        BitmapFactory.decodeResource(context.resources, id, mOptions)
//        val imageWidth = mOptions.outWidth
//        val imageHeight = mOptions.outHeight
//        mOptions.inSampleSize = calcuteInSampleSize(imageWidth, viewWidth)
        mOptions.inJustDecodeBounds = false;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inMutable = true;
        mOptions.inBitmap = reuseable;
        try {
            // bitmap复用可能会导致此Bug出现
            return BitmapFactory.decodeStream(inputStream, null, mOptions);
        } catch (Exception e) {
            reusepool.add(new WeakReference(reuseable));
            mOptions.inBitmap = null;
            try {
                // 为了后续重新读取数据使用
                inputStream.reset();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return BitmapFactory.decodeStream(inputStream, null, mOptions);
        }
    }

    private int calcuteInSampleSize(int imageWidth, int viewWidth) {
        int inSampleSize = 1;
        while (imageWidth / viewWidth > inSampleSize) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }
}
