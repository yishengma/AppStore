package piratehat.picture.loader;

import android.graphics.Bitmap;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import piratehat.picture.BitmapRequest;
import piratehat.picture.Picture;
import piratehat.picture.cache.IBitmapCache;


/**
 * Created by PirateHat on 2019/2/21.
 */

public abstract class BaseLoader implements ILoader {
    private IBitmapCache mBitmapCache = Picture.getInstance().getPictureConfig().getBitmapCache();
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap bitmap = mBitmapCache.get(request);
        if (request.isCancel()) {
            return;
        }
        //从网络加载
        if (bitmap == null) {
            showLoadingImage(request);
            bitmap = onLoad(request);

            if (request.isCancel()) {
                return;
            }
            cacheBitmap(request, bitmap);
        }
        deliveryToUI(request, bitmap);

    }

    private void showLoadingImage(final BitmapRequest request) {
        if (request.getDisplayConfig() != null) {
            final ImageView view = request.getImageView();
            if (view == null || request.getDisplayConfig().PLACE_HOLDRE == -1) {
                return;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageResource(request.getDisplayConfig().PLACE_HOLDRE);
                }
            });
        }
    }

    /**
     *  加载图片
     * @param request 请求
     * @return 结果
     */
    protected abstract Bitmap onLoad(BitmapRequest request);


    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (BaseLoader.this) {
                mBitmapCache.put(request, bitmap);
            }
        }
    }

    private void deliveryToUI(final BitmapRequest request, final Bitmap bitmap) {
        final ImageView view = request.getImageView();
        if (request.isCancel()) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if (bitmap != null && view != null && view.getTag().equals(request.getImageUrl())) {
                    assert view != null;
                    view.setImageBitmap(bitmap);
                }
                if (bitmap == null && view != null && request.getDisplayConfig() != null && request.getDisplayConfig().ERROR_HOLDER != -1) {
                    view.setImageResource(request.getDisplayConfig().ERROR_HOLDER);
                }
                if (request.getCallBack() != null) {
                    request.getCallBack().onSuccess(bitmap, request.getImageUrl());
                }

            }
        });
    }


}
