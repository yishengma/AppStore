package piratehat.picture;


import android.graphics.Bitmap;
import android.widget.ImageView;

import piratehat.picture.cache.DoubleCache;

import piratehat.picture.config.*;
import piratehat.picture.loader.policy.ReverseLoaderPolicy;


/**
 * Created by PirateHat on 2019/2/21.
 */

public class Picture {
    private volatile static Picture INSTANCE;
    private PictureConfig mPictureConfig;
    private RequestDispatcher mDispatcher;

    public Picture(PictureConfig pictureConfig) {
        if (pictureConfig!=null){
            mPictureConfig = pictureConfig;
        }else {
            mPictureConfig = new PictureConfig.Builder()
            .setBitmapCache(new DoubleCache())
            .setLoadPolicy(new ReverseLoaderPolicy())
            .build();
        }
        mDispatcher = new RequestDispatcher();
    }

    public static Picture getInstance(PictureConfig config){
        if (INSTANCE == null){
            synchronized (Picture.class){
                if (INSTANCE == null){
                    INSTANCE = new Picture(config);
                }
            }
        }
        return INSTANCE;
    }

    public static Picture getInstance(){
        return getInstance(null);
    }


    public PictureConfig getPictureConfig() {
        return mPictureConfig;
    }

    public void display(ImageView imageView, String url) {
        display(imageView, url, null, null);
    }

    public void display(ImageView imageView, String url, CallBack callback,DisplayConfig config) {
        BitmapRequest request = new BitmapRequest(url,imageView,callback,config);
        mDispatcher.enqueue(request);

    }


    public interface CallBack{

        void onSuccess(Bitmap bitmap,String imageUrl);

    }



}
