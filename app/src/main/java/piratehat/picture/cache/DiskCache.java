package piratehat.picture.cache;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.IOException;

import piratehat.appstore.app.App;
import piratehat.picture.BitmapRequest;
import piratehat.picture.cache.DiskLruCache.DiskLruCache.DiskLruCacheManager;

/**
 * Created by PirateHat on 2019/2/21.
 */

public class DiskCache implements IBitmapCache {
    private static final String IMAGE_DISK_NAME = "image_cache";
    private DiskLruCacheManager mDiskLruCacheManager;

    public DiskCache() {
        try {
            mDiskLruCacheManager = new DiskLruCacheManager(App.getContext(),IMAGE_DISK_NAME,1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
           mDiskLruCacheManager.put(request.getMD5CacheKey(),bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
         return   mDiskLruCacheManager.getAsBitmap(request.getMD5CacheKey());
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCacheManager.getDiskLruCache().remove(request.getMD5CacheKey());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
