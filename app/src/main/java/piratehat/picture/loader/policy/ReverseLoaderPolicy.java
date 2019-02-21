package piratehat.picture.loader.policy;

import piratehat.picture.BitmapRequest;

/**
 *
 * Created by PirateHat on 2019/2/21.
 */

public class ReverseLoaderPolicy implements ILoadPolicy {

    @Override
    public int compareTo(BitmapRequest req1, BitmapRequest req2) {
        return req2.getSerialNo()-req1.getSerialNo();
    }
}
