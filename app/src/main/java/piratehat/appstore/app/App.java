package piratehat.appstore.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class App extends Application {
    private static File mkdirs;
    private static File mCache;
    private static Context mContext;
    private static RefWatcher mRerWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRerWatcher = LeakCanary.install(this);

        mCache = getExternalCacheDir();

        mContext = getApplicationContext();


    }

    public static RefWatcher getmRerWatcher() {
        return mRerWatcher;
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
