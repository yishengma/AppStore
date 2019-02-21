package piratehat.picture.cache;

import android.graphics.Bitmap;

import piratehat.picture.BitmapRequest;

/**
 *
 * Created by PirateHat on 2019/2/21.
 */

public class DoubleCache implements IBitmapCache {
    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;

    public DoubleCache() {
        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache();
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mMemoryCache.put(request, bitmap);
        mDiskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        //先从内存中读取
        Bitmap bitmap = mMemoryCache.get(request);
        if (bitmap != null) {
            return bitmap;
        }
        //没有就从磁盘中获取
        bitmap = mDiskCache.get(request);
        if (bitmap != null) {
            //加入缓存
            mMemoryCache.put(request, bitmap);
            return bitmap;
        }

        return null;

    }

    @Override
    public void remove(BitmapRequest request) {
        mMemoryCache.remove(request);
        mDiskCache.remove(request);
    }
}
