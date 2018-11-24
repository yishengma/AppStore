package piratehat.appstore.module;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import piratehat.appstore.config.Constant;
import piratehat.appstore.config.Url;
import piratehat.appstore.contract.IRankContract;
import piratehat.appstore.utils.JsoupUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 * Created by PirateHat on 2018/11/23.
 */

public class RankModel implements IRankContract.IModel {

    @Override
    public void getRankMap(final IRankContract.IPresenter presenter) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.MAIN_PAGE, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {

                presenter.setRankMap((JsoupUtil.getInstance().getRankApps(msg)));


            }
        });
    }
}
