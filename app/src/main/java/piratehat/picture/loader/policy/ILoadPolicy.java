package piratehat.picture.loader.policy;

import piratehat.picture.BitmapRequest;

/**
 * Created by PirateHat on 2019/2/21.
 */

public interface ILoadPolicy {

     int compareTo(BitmapRequest req1,BitmapRequest req2);

}
