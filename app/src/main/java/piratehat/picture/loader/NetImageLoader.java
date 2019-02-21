package piratehat.picture.loader;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

import piratehat.appstore.app.App;
import piratehat.appstore.utils.CloseQuicklyUtil;
import piratehat.picture.BitmapDecoder;
import piratehat.picture.BitmapRequest;
import piratehat.picture.ImageViewHelper;


/**
 *
 * Created by PirateHat on 2019/2/21.
 */

public class NetImageLoader extends BaseLoader {

    private static final String TAG = "NetImageLoader";
    private static final boolean DEBUG = true;
    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
        downloadImage(request.getImageUrl(),new File(App.getDir(),request.getMD5CacheKey()+".jpg"));

        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(new File(App.getDir(),request.getMD5CacheKey()+".jpg").getAbsolutePath(), options);

            }
        };

        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));
    }

    private boolean downloadImage(String imageUrl, File file) {
        FileOutputStream fos = null;
        InputStream is = null;
       if (DEBUG){
           /*
           02-21 17:13:23.644 4120-4283/piratehat.appstore E/NetImageLoader: downloadImage: https://pp.myapp.com/ma_icon/0/icon_12127266_1548842214/256
02-21 17:13:23.644 4120-4283/piratehat.appstore E/NetImageLoader: downloadImage: /storage/emulated/0/imageLoader/43cfcfe77edaac224b613921fbfe28dc
            */
           Log.e(TAG, "downloadImage: "+imageUrl );
           Log.e(TAG, "downloadImage: "+file.getAbsolutePath());
       }
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10_000);

            is = connection.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[512];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseQuicklyUtil.close(is);
            CloseQuicklyUtil.close(fos);
        }

        return false;
    }
}
