package piratehat.appstore.module;


import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import piratehat.appstore.config.Constant;
import piratehat.appstore.config.Url;
import piratehat.appstore.contract.IBoutiqueContract;
import piratehat.appstore.utils.JsoupUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 * Created by PirateHat on 2018/11/23.
 */

public class BoutiqueModel implements IBoutiqueContract.IModel {

    @Override
    public void getBoutiqueMap(final IBoutiqueContract.IPresenter presenter) {



        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.MAIN_PAGE, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {

//                presenter.setBanner((ArrayList<BannerBean>) JsoupUtil.getInstance().getBanner(msg));
                presenter.setBoutiqueMap((JsoupUtil.getInstance().getRankApps(msg)));
//               presenter.setBoutiqueApps(JsoupUtil.getInstance().getBoutiqueApps(msg));
//                DiskCacheManager.getDiskInstance().put(Url.MAIN_PAGE,msg);

            }
        });
    }
}
