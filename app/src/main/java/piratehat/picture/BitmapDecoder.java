package piratehat.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by PirateHat on 2019/2/21.
 */

public abstract class BitmapDecoder {
    public Bitmap decodeBitmap(int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //从文件中读取 Bitmap
        decodeBitmapWithOption(options);

        //计算压缩比例
        calculateSampleSizeWithOption(options,width,height);


        options.inJustDecodeBounds = false;

        //再一次读取时进行压缩
        return decodeBitmapWithOption(options);
    }

    public abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);

    public void calculateSampleSizeWithOption(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            //宽高的缩放比例

            //舍入取整
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        options.inSampleSize = inSampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;


    }
}
