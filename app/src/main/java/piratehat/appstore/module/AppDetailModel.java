package piratehat.appstore.module;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.config.Constant;
import piratehat.appstore.config.Url;
import piratehat.appstore.contract.IAppDetailsContract;
import piratehat.appstore.dto.AppsDataDto;
import piratehat.appstore.utils.GsonUtil;
import piratehat.appstore.utils.JsoupUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 * Created by PirateHat on 2018/11/20.
 */

public class AppDetailModel implements IAppDetailsContract.IModel {

    @Override
    public void getAppDetailInfo(final IAppDetailsContract.IPresenter presenter, String apkName) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.DETAIL_INFO+apkName, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {
                presenter.setAppInfo(JsoupUtil.getInstance().getAppDetailInfo(msg));
            }
        }, map);
    }


}
