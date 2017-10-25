package com.kwaijian.facility.UI.BaseClass.Views.Browser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kwaijian.facility.UI.BaseClass.Views.Browser.PhotoView.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinGe on 2017/9/18.
 * 图片预览view
 */

public class HTImageBrowser extends FrameLayout implements ViewPager.OnPageChangeListener {

    /**
     * 分页
     */
    private ViewPager mImagePager;
    private ImagePagerAdapter mAdapter;
    private TextView mIndexView;//显示当前为第几的view

    /**
     * 表态图片缓存，如果app多处使用此对象，将共用一个图片缓存器
     */
    private static HTImageCache imageCache;


    public HTImageBrowser(@NonNull Context context) {
        this(context, null);
    }

    public HTImageBrowser(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HTImageBrowser(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (imageCache == null) {
            imageCache = new HTImageCache();
        }
        initChildViews();
    }

    /**
     * 初始化子view
     */
    private void initChildViews() {
        mImagePager = new ViewPager(getContext()) {
            @Override
            public boolean onTouchEvent(MotionEvent ev) {
                try {
                    return super.onTouchEvent(ev);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                try {
                    return super.onInterceptTouchEvent(ev);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        };
        mImagePager.addOnPageChangeListener(this);
        addView(mImagePager, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mAdapter = new ImagePagerAdapter();
        mImagePager.setAdapter(mAdapter);

        mIndexView = new TextView(getContext());
        mIndexView.setTextColor(Color.BLACK);
        mIndexView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.bottomMargin = 20;
        addView(mIndexView, params);
    }

    /**
     * 设置需要预览的图片数组
     * List 图片数据，可以是Url，Path，Bitmap的混合数据
     *
     * @param images
     */
    public void setImageDatas(List images) {
        if (images != null) {
            for (Object obj : images) {
                HTImageData data = createViewByData(obj);
                mAdapter.add(data);
            }
        }
        mAdapter.notifyDataSetChanged();
        setIndexText();
    }

    public void setIndex(int index) {
        mImagePager.setCurrentItem(index);
    }

    /**
     * 设置当前的页面位置标签
     */
    private void setIndexText() {
        mIndexView.setText((mImagePager.getCurrentItem() + 1) + "/" + mAdapter.getCount());
    }

    /**
     * 拿到数据后对数据进行分发处理
     *
     * @param obj
     */
    private HTImageData createViewByData(Object obj) {
        if (obj instanceof HTImageData) {
            return ((HTImageData) obj);
        } else {
            return new HTImageData(null, obj);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setIndexText();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 图片ViewPager的适配器
     */
    class ImagePagerAdapter extends PagerAdapter  {

        /**
         * 保存view列表
         */
        private List<HTImageData> datas = new ArrayList<>();
        private Map<HTImageData, View> viewCache = new HashMap<>();

        /**
         * 添加view到分页做显示
         *
         * @param data
         */
        public void add(HTImageData data) {
            datas.add(data);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            HTImageData data = datas.get(position);
            PhotoView photoView = new PhotoView(getContext());
            Picasso.with(getContext()).load(data.image.toString()).fit().centerInside().into(photoView);
            container.addView(photoView);
            viewCache.put(data, photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = viewCache.get(datas.get(position));
            container.removeView(view);
            viewCache.remove(datas.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
