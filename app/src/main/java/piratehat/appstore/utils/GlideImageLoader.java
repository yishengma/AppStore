package piratehat.appstore.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import piratehat.appstore.Bean.BannerBean;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(((BannerBean)path).getImageUrl()).into(imageView);
    }
}
