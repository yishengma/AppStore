package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.Bean.BannerBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.AppsAdapter;
import piratehat.appstore.contract.IMainContract;
import piratehat.appstore.presenter.MainPresenter;
import piratehat.appstore.utils.GlideImageLoader;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class MainFragment extends BaseFragment implements IMainContract.IView {


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.btn_download)
    ImageButton mBtnDownload;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    private AppsAdapter mAppsAdapter;
    private IMainContract.IPresenter mPresenter;
    private List<AppBean> mAppBeans;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(1500);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);

        mAppBeans = new ArrayList<>();
        mPresenter = new MainPresenter(this);
        mPresenter.getMainPage();

    }

    @Override
    protected void initView() {
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAppsAdapter = new AppsAdapter(mAppBeans, mActivity);
        mRvApps.setAdapter(mAppsAdapter);

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void setBanner(ArrayList<BannerBean> beans, ArrayList<String> titles) {
        mBanner.setImages(beans);
        mBanner.setBannerTitles(titles);
        mBanner.start();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setAppList(ArrayList<AppBean> appBeans) {
        mAppBeans.clear();
        mAppBeans.addAll(appBeans);
        mAppsAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_download)
    public void onViewClicked() {

    }

}
