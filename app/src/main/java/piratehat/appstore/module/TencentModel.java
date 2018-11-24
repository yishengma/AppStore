package piratehat.appstore.module;

import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.config.Constant;
import piratehat.appstore.config.Url;
import piratehat.appstore.contract.ITencentContract;

import piratehat.appstore.dto.AppsDataDto;

import piratehat.appstore.utils.GsonUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 *
 * Created by PirateHat on 2018/11/5.
 */

public class TencentModel implements ITencentContract.IModel {

    private int mPageContext = 0;
    private boolean mHasMore;

    @Override
    public void getAppsList(final ITencentContract.IPresenter presenter) {

        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.TENCENT_PAGE, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {
                ArrayList<AppBean> beans = (ArrayList<AppBean>) GsonUtil.gsonToBean(msg, AppsDataDto.class).transform();
                presenter.setAppsList(beans);
//                DiskCacheManager.getDiskInstance().put(Url.SOFTWARE_ALL,beans);

            }
        }, map);
    }

    @Override
    public RequestHandle refresh(ResponseSender<ArrayList<AppBean>> sender) throws Exception {
        return new OkHttpRequestHandle();
    }

    @Override
    public RequestHandle loadMore(ResponseSender<ArrayList<AppBean>> sender) throws Exception {
        return loadApps(sender);
    }

    @Override
    public boolean hasMore() {
        return mHasMore;
    }

    private RequestHandle loadApps(final ResponseSender<ArrayList<AppBean>> sender) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        mPageContext += 20;
        OkHttpUtil.getInstance().getAsync(Url.TENCENT_PAGE+mPageContext, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                sender.sendError(e);
            }

            @Override
            public void onResponse(String msg) {
                ArrayList<AppBean> beans = (ArrayList<AppBean>) GsonUtil.gsonToBean(msg, AppsDataDto.class).transform();
                mHasMore = beans.size() != 0;
                sender.sendData(beans);

            }
        }, map);

        return new OkHttpRequestHandle();
    }
}
