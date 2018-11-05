package piratehat.appstore.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.githang.statusbar.StatusBarCompat;
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
import piratehat.appstore.contract.ITencentContract;
import piratehat.appstore.presenter.TencentPresenter;

public class TencentActivity extends BaseActivity implements ITencentContract.IView {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;
    private List<AppBean> mAppBeans;

    private MVCHelper<List<AppBean>> mMVCHelper;
    private CommonAppsAdapter mAdapter;
    private TencentPresenter mPresenter;


    @Override
    protected int setResId() {
        return R.layout.activity_tencent;
    }

    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorBackgroundWhite), true);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("腾讯");

        mAdapter = new CommonAppsAdapter(this, mAppBeans);
        mRvApps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvApps.setAdapter(mAdapter);
        mMVCHelper = new MVCCoolHelper<>(mCrvApps);
        mMVCHelper.setDataSource((IAsyncDataSource<List<AppBean>>) mPresenter.loadMore());
        mMVCHelper.setAdapter(mAdapter);

    }

    @Override
    protected void initData(Bundle bundle) {
        mAppBeans = new ArrayList<>();
        mPresenter = new TencentPresenter(this);
        mPresenter.getAppsList();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void setAppsList(ArrayList<AppBean> beans) {
        mAppBeans.addAll(beans);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showError(String msg) {

    }
}
