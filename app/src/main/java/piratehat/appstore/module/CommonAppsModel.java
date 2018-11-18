package piratehat.appstore.module;

import android.util.Log;

import com.shizhefei.mvc.IAsyncDataSource;
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
import piratehat.appstore.contract.IAppsContract;
import piratehat.appstore.dto.AppsDataDto;
import piratehat.appstore.dto.CategoryAppsDto;
import piratehat.appstore.utils.GsonUtil;
import piratehat.appstore.utils.JsoupUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public class CommonAppsModel implements IAppsContract.IModel {
    private static final String TAG = "CommonAppsModel";
    private String mCategory;
    private boolean mHasMore;
    private int mIndex = 1;



    public CommonAppsModel(String category) {
        mCategory = category;
    }


    @Override
    public RequestHandle refresh(ResponseSender<List<AppBean>> sender) throws Exception {
        return new OkHttpRequestHandle();
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<AppBean>> sender) throws Exception {

        return loadApps(sender, Url.MORE_MODE[mIndex++]);
    }

    @Override
    public boolean hasMore() {
        return mHasMore;
    }

    @Override
    public void getCategory(final IAppsContract.IPresenter presenter, String category) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.CATEGORY + mCategory+Url.MORE_MODE[0], new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {
                ArrayList<AppBean> beans = (ArrayList<AppBean>) GsonUtil.gsonToBean(msg, CategoryAppsDto.class).transform();
                presenter.setResult(beans);
            }
        }, map);
    }

    private RequestHandle loadApps(final ResponseSender<List<AppBean>> sender, String mode) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.CATEGORY + mCategory + mode, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                sender.sendError(e);
            }

            @Override
            public void onResponse(String msg) {
                ArrayList<AppBean> beans = (ArrayList<AppBean>) GsonUtil.gsonToBean(msg, CategoryAppsDto.class).transform();
                mHasMore = beans.size() != 0;
                sender.sendData(beans);

            }
        }, map);

        return new OkHttpRequestHandle();
    }


    private class OkHttpRequestHandle implements RequestHandle {


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
}