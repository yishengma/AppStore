package piratehat.appstore.utils;

import android.annotation.SuppressLint;

/**
 *
 * Created by PirateHat on 2018/10/28.
 */

public class UnitUtil {
    private static final String BILLION_DOWNLOAD ="亿次下载";
    private static final String MILLION_DOWNLOAD = "万次下载";
    private static final String DOWNLOAD = "次下载";



    public static String converse(int i){
        if (i<10000){
            return i+DOWNLOAD;
        }
        if (i<100000000){
            return i/10000 + MILLION_DOWNLOAD;
        }
        return i/100000000 +BILLION_DOWNLOAD;
    }

    public static String converse(long i){
        if (i<10000){
            return i+DOWNLOAD;
        }
        if (i<100000000){
            return i/10000 + MILLION_DOWNLOAD;
        }
        return i/100000000 +BILLION_DOWNLOAD;
    }
    @SuppressLint("DefaultLocale")
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

}
