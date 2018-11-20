package piratehat.appstore.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.Bean.BannerBean;
import piratehat.appstore.Bean.SerializableMap;
import piratehat.appstore.R;
import piratehat.appstore.adapter.MainAppsAdapter;
import piratehat.appstore.contract.IMainContract;
import piratehat.appstore.presenter.MainPresenter;
import piratehat.appstore.ui.AppDetailsActivity;
import piratehat.appstore.ui.BoutiqueActivity;
import piratehat.appstore.ui.CategoryActivity;
import piratehat.appstore.ui.RankActivity;
import piratehat.appstore.ui.TencentActivity;


/**
 * Created by PirateHat on 2018/10/27.
 */

public class MainFragment extends BaseFragment implements IMainContract.IView {


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.btn_download)
    ImageButton mBtnDownload;

    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;

    private MainAppsAdapter mAppsAdapter;
    private IMainContract.IPresenter mPresenter;
    private MVCHelper<List<AppBean>> mMVCHelper;
    private Map<String, List<AppBean>> mRankMap;
    private Map<String, List<AppBean>> mBoutiqueMap;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData(Bundle bundle) {

        mPresenter = new MainPresenter(this);
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mMVCHelper = new MVCCoolHelper<>(mCrvApps);
        mMVCHelper.setDataSource(mPresenter.getRefreshData());
        mAppsAdapter = new MainAppsAdapter(mActivity);
        mMVCHelper.setAdapter(mAppsAdapter);
        mRankMap = new HashMap<>();
        mBoutiqueMap = new HashMap<>();
        mPresenter.getMainPage();
        mMVCHelper.refresh();


    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initListener() {
        mAppsAdapter.setTabListener(new MainAppsAdapter.OnTabClickListener() {
            @Override
            public void click(int id) {
                switch (id) {
                    case R.id.tab_category:
                        CategoryActivity.actionStart(mActivity);
                        break;
                    case R.id.tab_rank:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("map", new SerializableMap(mRankMap));
                        RankActivity.actionStart(mActivity, bundle);
                        break;
                    case R.id.tab_tencent:
                        TencentActivity.actionStart(mActivity, TencentActivity.class);
                        break;
                    case R.id.tab_boutique:
                        Bundle bundle1 = new Bundle();
                        SerializableMap map = new SerializableMap(mBoutiqueMap);
                        bundle1.putSerializable("map", map);
                        BoutiqueActivity.actionStart(mActivity, BoutiqueActivity.class, bundle1);
                        break;
                    default:
                        break;
                }
            }
        });
        mAppsAdapter.setOnClickListener(new MainAppsAdapter.OnClickListener() {
            @Override
            public void click(String apkName) {
                AppDetailsActivity.actionStart(mActivity, apkName);
            }
        });

    }

    @Override
    public void setRankApps(Map<String, List<AppBean>> beans) {
        mRankMap = beans;
    }

    @Override
    public void setBanner(ArrayList<BannerBean> beans, ArrayList<String> titles) {
        mAppsAdapter.startBanner(beans, titles);
    }

    @Override
    public void showError(String msg) {

    }

    @OnClick(R.id.btn_download)
    public void onViewClicked() {

    }

    @Override
    public void setBoutiqueApps(Map<String, List<AppBean>> beans) {
        mBoutiqueMap = beans;
    }
}
