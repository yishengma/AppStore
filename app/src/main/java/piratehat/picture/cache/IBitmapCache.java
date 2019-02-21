package piratehat.picture.cache;

import android.graphics.Bitmap;

import piratehat.picture.BitmapRequest;

/**
 * Created by PirateHat on 2019/2/21.
 */

public interface IBitmapCache {

    /**
     * 添加缓存
     * @param request 请求
     * @param bitmap 图片
     */
    void put(BitmapRequest request, Bitmap bitmap);


    /**
     * 从缓存中获取
     * @param request 请求
     */
    Bitmap get(BitmapRequest request);


    /**
     * 移除缓存
     * @param request 请求
     */
    void remove(BitmapRequest request);
}
