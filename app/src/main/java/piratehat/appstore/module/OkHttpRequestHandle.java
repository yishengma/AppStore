package piratehat.appstore.module;

import com.shizhefei.mvc.RequestHandle;

import piratehat.appstore.utils.OkHttpUtil;

/**
 * Created by PirateHat on 2018/11/5.
 */

public class OkHttpRequestHandle  implements RequestHandle {


    OkHttpRequestHandle() {
        super();

    }

    @Override
    public void cancle() {
        OkHttpUtil.getInstance().cancelAllRequest();
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
