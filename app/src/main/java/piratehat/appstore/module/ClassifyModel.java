package piratehat.appstore.module;

import android.util.Log;

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
import piratehat.appstore.utils.GsonUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 *
 * Created by PirateHat on 2018/11/18.
 */

public class ClassifyModel implements IAppsContract.IModel {

    private static final String TAG = "ClassifyModel";
    private boolean mHasMore;
    private int mID = -1;
    private int mPageContext;

    public ClassifyModel(int ID) {
        mID = ID;
        Log.e(TAG, "ClassifyModel: "+mID );
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<AppBean>> sender) throws Exception {
        return new OkHttpRequestHandle();
     }

    @Override
    public RequestHandle loadMore(ResponseSender<List<AppBean>> sender) throws Exception {
        mPageContext+=20;
        return loadApps(sender);
    }

    @Override
    public boolean hasMore() {
        return mHasMore;
    }

    @Override
    public void getCategory(final IAppsContract.IPresenter presenter, String category) {

//        final List list;
//        if ((list=DiskCacheManager.getDiskInstance().getList(Url.CLASSIFY_ROOT + mID + Url.CLASSIFY_CONTEXT,AppBean.class))!=null&&list.size()!=0){
//            presenter.setResult((ArrayList<AppBean>) list);
//            return;
//        }

        final Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);

        OkHttpUtil.getInstance().getAsync(Url.CLASSIFY_ROOT + mID + Url.CLASSIFY_CONTEXT + mPageContext, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {

                ArrayList<AppBean> beans = (ArrayList<AppBean>) GsonUtil.gsonToBean(msg, AppsDataDto.class).transform();
                mHasMore = beans.size() != 0;
                presenter.setResult(beans);
//                DiskCacheManager.getDiskInstance().put(Url.CLASSIFY_ROOT + mID + Url.CLASSIFY_CONTEXT,beans);


            }
        }, map);
    }

    private RequestHandle loadApps(final ResponseSender<List<AppBean>> sender) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.CLASSIFY_ROOT + mID + Url.CLASSIFY_CONTEXT + mPageContext, new OkHttpResultCallback() {
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
