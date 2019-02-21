package piratehat.picture.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;

import piratehat.picture.BitmapDecoder;
import piratehat.picture.BitmapRequest;
import piratehat.picture.ImageViewHelper;

/**
 *
 * Created by PirateHat on 2019/2/21.
 */

public class DiskImageLoader extends BaseLoader {

    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        final String path= Uri.parse(request.getImageUrl()).getPath();
        File file = new File(path);
        if (!file.exists()){
            return null;
        }

        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path,options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
