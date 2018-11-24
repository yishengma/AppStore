package piratehat.appstore.adapter;

import android.content.Context;

import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import piratehat.appstore.R;

/**
 *
 * Created by PirateHat on 2018/11/21.
 */

public class ImageAdapter extends PagerAdapter {

    private List<String> mUrl;
    private Context mContext;

    public ImageAdapter(List<String> url, Context context) {
        mUrl = url;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mUrl.size();
    }

    /**
     * 该函数用来判断instantiateItem(ViewGroup, int)
     * 函数所返回来的Key与一个页面视图是否是代表的同一个视图(即它俩是否是对应的，对应的表示同一个View)
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        //几乎是固定的写法,
        return view == object;
    }

    /**
     * 返回要显示的view,即要显示的视图
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_image, null);
        ImageView imageView = view.findViewById(R.id.imv_icon);
        String url = mUrl.get(position);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(mContext).load(url).into(imageView);
        }

        container.addView(view);    //这一步很重要
        return view;
    }

    /**
     * 销毁条目
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return  (float) 0.33;
    }
}


