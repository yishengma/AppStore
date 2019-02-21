package piratehat.picture.cache;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.LruCache;

import piratehat.picture.BitmapRequest;

/**
 * Created by PirateHat on 2019/2/21.
 */

public class MemoryCache implements IBitmapCache {

    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 4); //内存的 1/4 作为缓存
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int size = value.getRowBytes() * value.getHeight();// 返回每一项在内存中大小
                return size;
            }
        };
    }
/*
* 1、getRowBytes：Since API Level 1，用于计算位图每一行所占用的内存字节数。
2、getByteCount：Since API Level 12，用于计算位图所占用的内存字节数。
经实测发现：getByteCount() = getRowBytes() * getHeight()，也就是说位图所占用的内存空间数等于位图的每一行所占用的空间数乘以位图的行数。
因为getByteCount要求的API版本较高，因此对于使用较低版本的开发者，在计算位图所占空间时上面的方法或许有帮助*/
    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mLruCache.put(request.getMD5CacheKey(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mLruCache.get(request.getMD5CacheKey());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getMD5CacheKey());
    }
}
