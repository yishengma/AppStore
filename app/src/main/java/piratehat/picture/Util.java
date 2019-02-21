package piratehat.picture;

/**
 * Created by PirateHat on 2019/2/21.
 */

public class Util {
//    public  static  File getCache(String unique) {
////        File file = new File(Environment.getExternalStorageDirectory(), "image");
////        if (!file.exists()) {
////            file.mkdir();
////        }
////        return new File(file, unique);
//
//    }
//    public static File getDir() {
//        if (mkdirs != null && mkdirs.exists()) {
//            return mkdirs;
//        }
//
//        mkdirs = new File(mCache, "download");
//        if (!mkdirs.exists()) {
//            mkdirs.mkdirs();
//        }
//        return mkdirs;
//    }

    public static String parseURL(String imageURL) {
        if (imageURL.contains("://")) {
            return imageURL.split("://")[0];
        }

//        L.e("不支持的URL：" + imageURL);
        return " ";

    }
}
