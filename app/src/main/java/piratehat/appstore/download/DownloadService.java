package piratehat.appstore.download;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PirateHat on 2018/11/24.
 */

public class DownloadService extends Service {

    private DownloadTask mDownloadTask;

    private String mDownloadUrl;

    private static Map<String,DownloadListener> mListenerMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mListenerMap = new HashMap<>();
    }

    public static void addListener(String downloadUrl,DownloadListener listener){
        mListenerMap.put(downloadUrl,listener);
    }

    public static void removeLitener(String downloadUrl){
        mListenerMap.remove(downloadUrl);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private DownloadBinder mBinder = new DownloadBinder();


   public class DownloadBinder extends Binder {

        /**
         * 开始下载
         *
         * @param url
         */
        public void startDownload(String url,String name) {
                mDownloadUrl = url;
                mDownloadTask = new DownloadTask(mListenerMap.get(mDownloadUrl));
                //启动下载任务
                mDownloadTask.execute(mDownloadUrl,name+".apk");
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();

        }

        /**
         * 暂停下载
         */
        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pauseDownload();
            }
        }

        /**
         * 取消下载
         */
        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.cancelDownload();
            } else {
                if (mDownloadUrl != null) {
                    //取消下载时需要将文件删除
                    String fileName = mDownloadUrl.substring(mDownloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }

                    Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }

        }


    }
}
