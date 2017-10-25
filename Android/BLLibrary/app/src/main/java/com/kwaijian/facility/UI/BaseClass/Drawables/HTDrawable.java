package com.kwaijian.facility.UI.BaseClass.Drawables;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by BinGe on 2017/9/20.
 * 加载网络或本地图片
 */

public class HTDrawable extends HTImageDrawable {

    /**
     * 在子线程加载网络或本地图片，所有Drawable共用一个
     */
    private static Handler sChildHandler;
    private static Handler sMainHandler;

    /**
     * 加载动画
     */
    private static Bitmap sLoadingBitmap;

    /**
     * 用于计算loading时间
     */
    private long time;

    private Paint mPaint;

    private LoadCompletedCallback mLoadCompletedCallback;

    private AssetManager mAssetManager;
    private String mSource;



    public HTDrawable(Bitmap bitmap) {
        super(bitmap);
    }

    public HTDrawable(String source) {
        super();
        init();
        setImageSource(source);

    }

    public HTDrawable(AssetManager am, String source) {
        super();
        init();
        mAssetManager = am;
        mSource = source;
        setImageSource(source);
    }

    private void init() {
        if (sChildHandler == null) {
            HandlerThread ht = new HandlerThread(getClass().getName());
            ht.start();
            sChildHandler = new Handler(ht.getLooper());
        }

        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setDensity(int density) {
        super.setDensity(density);
        if (sLoadingBitmap != null) {
            sLoadingBitmap.recycle();
            sLoadingBitmap = null;
        }
    }

    public void setImageSource(final String source) {
        mSource = source;
        if (source != null) {
            sChildHandler.post(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap;
                    if (source.startsWith("http")) {
                        bitmap = loadNetBitmap(source);
                    }
                    else if (source.startsWith("file:///android_assets")) {
                        bitmap = loadAssetBitmap(source);
                    }
                    else {
                        bitmap = loadLocalBitmap(source);
                    }
                    sMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mLoadCompletedCallback != null) {
                                mLoadCompletedCallback.onLoadCompleted(mSource, bitmap);
                            }
                            if (!isRecycle()) {
                                setImageBitmap(bitmap);
                            }
                            invalidateSelf();
                        }
                    });
                }
            });
        }
    }

    public void setLoadCompletedCallback(LoadCompletedCallback cb) {
        mLoadCompletedCallback = cb;
    }

    /**
     * 加载网络图片
     * 这里到时可以用其它方法来加载，暂时使用原生加载方法
     * @param source
     * @return
     */
    private Bitmap loadNetBitmap(String source) {
        try {
            BitmapDrawable d = (BitmapDrawable) Drawable.createFromStream(new URL(source).openStream(), source);
            return d.getBitmap();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加载本地图片
     *
     * @param source
     * @return
     */
    private Bitmap loadLocalBitmap(String source) {
        try {
            Bitmap b = BitmapFactory.decodeFile(source);
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    private Bitmap loadAssetBitmap(String source) {
        try {
            String path = source.replace("file:///android_assets/","");
            path = path.replace("file:///android_asset/","");
            InputStream is = mAssetManager.open(path);
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //如果已经有了bitmap直接显示
        if (getImageBitmap() != null) {
            super.draw(canvas);
        }
        //如果bitmap还没有，则显示一个加载动画
        else {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            if (time == 0) {
                time = System.currentTimeMillis();
            }
            float t = (System.currentTimeMillis() - time) / 1000f;
            t = t % 1;
            int angle = (int) (360 * t);
            canvas.save();
            canvas.rotate(angle, width / 2, height / 2);

            Bitmap loading = getLoadingBitmap();
            canvas.drawBitmap(loading,
                    width / 2 - loading.getWidth() / 2,
                    height / 2 - loading.getHeight() / 2, null);
            canvas.restore();

            invalidateSelf();
        }
    }

    /**
     * 创建loading图片
     *
     * @return
     */
    private Bitmap getLoadingBitmap() {
        if (sLoadingBitmap == null) {
            int width = 40 * getDensity();
            sLoadingBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(sLoadingBitmap);
            Paint p = new Paint();
            float stroke = width * 0.02f;
            p.setStrokeWidth(width * 0.03f);
            p.setStyle(Paint.Style.STROKE);
            p.setAntiAlias(true);
            p.setColor(Color.BLACK);
            p.setAlpha(150);
            c.drawArc(new RectF(0 + stroke, 0 + stroke, width - stroke, width - stroke), 20, 360 - 40, false, p);
        }
        return sLoadingBitmap;
    }

    public interface LoadCompletedCallback {
        public void onLoadCompleted(String source, Bitmap bitmap);
    }

}
