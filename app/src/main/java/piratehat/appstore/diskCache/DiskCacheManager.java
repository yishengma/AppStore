package piratehat.appstore.diskCache;

import android.content.Context;
import android.os.Environment;


import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import piratehat.appstore.app.App;
import piratehat.appstore.config.Constant;
import piratehat.appstore.utils.CloseQuicklyUtil;
import piratehat.appstore.utils.SecretUtil;


/**
 *
 * Created by PirateHat on 2018/11/22.
 */
@Deprecated
public class DiskCacheManager {
    private DiskLruCache mDiskLruCache = null;
    private DiskLruCache.Editor mEditor = null;
    private DiskLruCache.Snapshot mSnapshot = null;
    private static DiskCacheManager mCacheManager;

    private DiskCacheManager() {
        try {
            File cacheFile = getCacheFile(App.getContext(), "AppInfoCache");
            mDiskLruCache = DiskLruCache.open(cacheFile, App.getAppVersionCode(), 1, Constant.CACHE_MAXSIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskCacheManager getDiskInstance(){
        if (mCacheManager == null) {
            mCacheManager = new DiskCacheManager();

        }
        return mCacheManager;
    }

    /**
     * 获取缓存的路径 两个路径在卸载程序时都会删除，因此不会在卸载后还保留乱七八糟的缓存
     * 有SD卡时获取  /sdcard/Android/data/<application package>/cache
     * 无SD卡时获取  /data/data/<application package>/cache
     *
     * @param context    上下文
     * @param uniqueName 缓存目录下的细分目录，用于存放不同类型的缓存
     * @return 缓存目录 File
     */
    private File getCacheFile(Context context, String uniqueName) {
        String cachePath = null;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取缓存 editor
     *
     * @param key 缓存的key
     * @return editor
     * @throws IOException
     */
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
    /*************************
     * 字符串读写
     *************************/
    /**
     * 缓存 String
     *
     * @param key   缓存文件键值（MD5加密结果作为缓存文件名）
     * @param value 缓存内容
     */
    public void put(String key, String value) {
        DiskLruCache.Editor editor = null;
        BufferedWriter writer = null;
        try {
            editor = edit(key);
            if (editor == null) {
                return;
            }
            OutputStream os = editor.newOutputStream(0);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(value);
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
            CloseQuicklyUtil.close(writer);

        }
        flush();
    }

    /**
     * 获取字符串缓存
     *
     * @param key cache'key
     * @return string
     */
    public String getString(String key) {
        InputStream inputStream = getCacheInputStream(key);
        if (inputStream == null) {
            return null;
        }
        try {
            return inputStream2String(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseQuicklyUtil.close(inputStream);
        }
    }





    /**
     * 序列化对象写入
     *
     * @param key    cache'key
     * @param object 待缓存的序列化对象
     */
    private void put(String key, Serializable object) {
        ObjectOutputStream oos = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = edit(key);
            if (editor == null) {
                return;
            }
            oos = new ObjectOutputStream(editor.newOutputStream(0));
            oos.writeObject(object);
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
    }

    /**
     * 获取 序列化对象
     *
     * @param key cache'key
     * @param <T> 对象类型
     * @return 读取到的序列化对象
     */
    @SuppressWarnings("unchecked")
    private <T> T getSerializable(String key) {
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


    /************************************************************************
     **********************  辅助工具方法 分割线  ****************************
     ************************************************************************/
    /**
     * inputStream 转 String
     *
     * @param is 输入流
     * @return 结果字符串
     */
    private String inputStream2String(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
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

    /**
     * 同步记录文件
     */
    private  void flush() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
