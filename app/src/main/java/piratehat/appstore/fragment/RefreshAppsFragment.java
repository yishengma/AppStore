package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.CommonAppsAdapter;
import piratehat.appstore.app.App;
import piratehat.appstore.contract.IAppsContract;
import piratehat.appstore.presenter.AppPresenter;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public class RefreshAppsFragment extends BaseFragment implements IAppsContract.IView {

    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;


    private List<AppBean> mAppBeans;
    private CommonAppsAdapter mCommonAppsAdapter;
    private String mCategory;
    private AppPresenter mPresenter;
    private MVCHelper<List<AppBean>> mMVCHelper;
    private int mCategoryId;
    private static final String TAG = "RefreshAppsFragment";

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){
            mPresenter.getCategory(mCategory);
        }
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_apps;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAppBeans = new ArrayList<>();
        mCategory = bundle.getString("category");
        mCategoryId = bundle.getInt("categoryId");
        if (!TextUtils.isEmpty(mCategory)){
            mPresenter = new AppPresenter(this, mCategory);
        }else {
            mPresenter = new AppPresenter(this,mCategoryId);
        }

    }

    @Override
    protected void initView() {
        mCommonAppsAdapter = new CommonAppsAdapter(mActivity, mAppBeans);
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRvApps.setAdapter(mCommonAppsAdapter);
        mMVCHelper = new MVCCoolHelper<>(mCrvApps);
        mMVCHelper.setDataSource((IAsyncDataSource<List<AppBean>>) mPresenter.getAppsData(mCategory));
        mMVCHelper.setAdapter(mCommonAppsAdapter);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void setResult(ArrayList<AppBean> beans) {
        mAppBeans.addAll(beans);
        mCommonAppsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {

    }

    public static RefreshAppsFragment newInstance(String category) {
        RefreshAppsFragment fragment = new RefreshAppsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static RefreshAppsFragment newInstance(int id) {
        RefreshAppsFragment fragment = new RefreshAppsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getmRerWatcher().watch(this);
    }

}
