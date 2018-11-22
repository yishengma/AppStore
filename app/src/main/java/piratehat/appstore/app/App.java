package piratehat.appstore.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public class App extends Application {
    private static File mkdirs;
    private static File mCache;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mCache = getExternalCacheDir();

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取应用的版本号
     *
     * @return 应用版本号，默认返回1
     */
    public static int getAppVersionCode() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static File getDir() {
        if (mkdirs != null && mkdirs.exists()) {
            return mkdirs;
        }

        mkdirs = new File(mCache, "download");
        if (!mkdirs.exists()) {
            mkdirs.mkdirs();
        }
        return mkdirs;
    }
}
