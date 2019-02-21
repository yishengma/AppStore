package piratehat.picture;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;



/**
 *
 * Created by PirateHat on 2019/2/21.
 */

public class RequestDispatcher {

    private static final boolean DEBUG = true;
    private ExecutorService mExecutorService;
    private AtomicInteger mInteger ;
    public RequestDispatcher() {
        mInteger = new AtomicInteger(0);
        mExecutorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors()
                , Integer.MAX_VALUE, 60,
                TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
    }

    public void enqueue(BitmapRequest request){
        request.setSerialNo(mInteger.getAndDecrement());
        if (request.getDisplayConfig() != null
                && request.getDisplayConfig().PLACE_HOLDRE != -1) {
            ImageView imageView = request.getImageView();
            if (imageView != null && imageView.getTag().equals(request.getImageUrl())) {
                imageView.setImageResource(request.getDisplayConfig().PLACE_HOLDRE);

            }
        }
        mExecutorService.execute(new RequestRunnable(request));
    }

}
