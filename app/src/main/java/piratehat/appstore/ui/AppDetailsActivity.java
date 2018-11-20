package piratehat.appstore.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppDetailBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.ViewPagerAdapter;
import piratehat.appstore.contract.IAppDetailsContract;
import piratehat.appstore.fragment.ImageFragment;
import piratehat.appstore.presenter.AppDetailPresenter;


public class AppDetailsActivity extends BaseActivity implements IAppDetailsContract.IView {

    @BindView(R.id.imv_icon)
    ImageView mImvIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_hot)
    TextView mTvHot;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    @BindView(R.id.tv_introduction)
    TextView mTvIntroduction;
    private AppDetailPresenter mPresenter;
    private ViewPagerAdapter mAdapter;


    @Override
    protected int setResId() {
        return R.layout.activity_app_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle bundle) {
        mPresenter = new AppDetailPresenter(this);
        String apkName = bundle.getString("apkName");
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mVpContent.setAdapter(mAdapter);
        mPresenter.getAppDetailInfo(apkName);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void setAppInfo(AppDetailBean appInfo) {
        Glide.with(this).load(appInfo.getIcon()).into(mImvIcon);
        mTvName.setText(appInfo.getName());
        mTvScore.setText(appInfo.getScore());
        mTvHot.setText(appInfo.getBaseInfo());
        mTvInfo.setText(appInfo.getInfo());
        mTvIntroduction.setText(appInfo.getDetailInfo());
        for (int i = 0, size = appInfo.getImageList().size(); i < size; i++) {
            mAdapter.addFragment(ImageFragment.newInstance(appInfo.getImageList().get(i)));
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static void actionStart(Context context, String apkName) {
        Bundle bundle = new Bundle();
        bundle.putString("apkName", apkName);
        Intent intent = new Intent(context, AppDetailsActivity.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);

    }


}
