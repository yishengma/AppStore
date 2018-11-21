package piratehat.appstore.app;

import android.app.Application;

import java.io.File;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public class App extends Application{
    private static File mkdirs;
    private static File mCache;
    @Override
    public void onCreate() {
        super.onCreate();
        mCache=   getExternalCacheDir();

    }



    public static File getDir(){
        if (mkdirs!=null && mkdirs.exists()){
            return mkdirs;
        }

        mkdirs = new File(mCache, "download");
        if (!mkdirs.exists()){
            mkdirs.mkdirs();
        }
        return mkdirs;
    }
}
