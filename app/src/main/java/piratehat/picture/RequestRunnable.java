package piratehat.picture;


import piratehat.picture.loader.ILoader;
import piratehat.picture.loader.LoaderManager;

/**
 *
 * Created by PirateHat on 2019/2/21.
 */

public class RequestRunnable implements Runnable {

    private BitmapRequest mBitmapRequest;

    public RequestRunnable(BitmapRequest request) {
        mBitmapRequest = request;
    }

    @Override
    public void run() {
        if (mBitmapRequest.isCancel()) {
            return;
        }
        ILoader loader = LoaderManager.getInstance().getLoaderByType(Util.parseURL(mBitmapRequest.getImageUrl()));
        loader.loadImage(mBitmapRequest);

    }
}
