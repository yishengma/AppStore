package piratehat.appstore.utils;

import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import piratehat.appstore.app.App;

/**
 * Created by PirateHat on 2018/11/24.
 */

public class CacheUtil {

    private static CacheUtil sCacheUtil;
    private LruCache<String, List> mLruCache;
    private DiskLruCache mDiskLruCache;
    private DiskLruCache.Editor mEditor = null;
    private DiskLruCache.Snapshot mSnapshot = null;


    private CacheUtil() {
        initCache();
    }

    private void initCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;
        mLruCache = new LruCache<>(cacheSize);

        try {
            File cacheDir = getDiskCacheDir("AppData");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, App.getAppVersionCode(), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static CacheUtil getInstance() {
        if (sCacheUtil == null) {
            sCacheUtil = new CacheUtil();
        }
        return sCacheUtil;
    }

    /**
     * 两种缓存的 get
     */
    public List get(String url) {
        List list;
        if ((list = getLru(url)) == null) {
            list = getDisk(url);
        }

        return list;

    }

    /**
     * 两种缓存的 put
     */

    public void put(final String url, final List list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                putLru(url, list);
                putDisk(url, list);
            }
        }).start();

    }

   /* ********************************************************************** */

    /**
     * LruCache  的缓存
     *
     * @param url  url
     * @param list list
     */
    private void putLru(String url, List list) {

        mLruCache.put(SecretUtil.getMD5Result(url), list);
    }

    private List getLru(String url) {

        return mLruCache.get(SecretUtil.getMD5Result(url));
    }

   /* ********************************************************************** */

    /**
     *  DiskLruCache 的缓存
     * @param key
     * @return
     * @throws IOException
     */

    /**
     * 获取 序列化对象
     *
     * @param key cache'key
     * @param <T> 对象类型
     * @return 读取到的序列化对象
     */
    @SuppressWarnings("unchecked")
    private <T> T getDisk(String key) {

        T object = null;
        ObjectInputStream ois = null;
        InputStream in = getCacheInputStream(key);
        if (in == null) {
            return null;
        }
        try {
            ois = new ObjectInputStream(in);
            object = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    private void putDisk(String key, List list) {
        ObjectOutputStream oos = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = edit(key);
            if (editor == null) {
                return;
            }
            oos = new ObjectOutputStream(editor.newOutputStream(0));
            oos.writeObject(list);
            oos.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (editor != null)
                    editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            CloseQuicklyUtil.close(oos);
        }
        flush();

    }


    private DiskLruCache.Editor edit(String key) throws IOException {
        key = SecretUtil.getMD5Result(key); //存取的 key
        if (mDiskLruCache != null) {
            mEditor = mDiskLruCache.edit(key);
        }
        return mEditor;
    }

    /**
     * 根据 key 获取缓存缩略
     *
     * @param key 缓存的key
     * @return Snapshot
     */
    private DiskLruCache.Snapshot snapshot(String key) {
        if (mDiskLruCache != null) {
            try {
                mSnapshot = mDiskLruCache.get(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mSnapshot;
    }

    /**
     * 获取 缓存数据的 InputStream
     *
     * @param key cache'key
     * @return InputStream
     */
    private InputStream getCacheInputStream(String key) {
        key = SecretUtil.getMD5Result(key);
        InputStream in;
        DiskLruCache.Snapshot snapshot = snapshot(key);
        if (snapshot == null) {
            return null;
        }
        in = snapshot.getInputStream(0);
        return in;
    }


    private File getDiskCacheDir(String uniqueName) {

        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = App.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = App.getContext().getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 同步记录文件
     */
    private void flush() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   /* ********************************************************************** */

}
