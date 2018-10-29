package piratehat.appstore.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;
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
import piratehat.appstore.module.AppsDataSource;
import piratehat.appstore.presenter.MainPresenter;
import piratehat.appstore.utils.GlideImageLoader;

/**
 *
 *
 * Created by PirateHat on 2018/10/27.
 */

public class MainFragment extends BaseFragment implements IMainContract.IView {


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.btn_download)
    ImageButton mBtnDownload;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;


    private IMainContract.IPresenter mPresenter;
    private MVCHelper<List<AppBean>> mMVCHelper;

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


        mPresenter = new MainPresenter(this);
        mPresenter.getMainPage();

        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity));
        mMVCHelper = new MVCCoolHelper<>(mCrvApps);
        mMVCHelper.setDataSource(new AppsDataSource());
        mMVCHelper.setAdapter(new AppsAdapter(mActivity));
        mMVCHelper.refresh();

    }

    @Override
    protected void initView() {


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

    @OnClick(R.id.btn_download)
    public void onViewClicked() {

    }


}
