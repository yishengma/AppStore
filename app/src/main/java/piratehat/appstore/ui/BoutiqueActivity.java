package piratehat.appstore.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

import java.util.Map;

import butterknife.BindView;



import piratehat.appstore.R;
import piratehat.appstore.adapter.ViewPagerAdapter;

import piratehat.appstore.contract.IBoutiqueContract;

import piratehat.appstore.fragment.NormalAppsFragment;
import piratehat.appstore.presenter.BoutiquePresenter;

public class BoutiqueActivity extends BaseActivity implements IBoutiqueContract.IView{


    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tl_navigation)
    TabLayout mTlNavigation;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    private static final String TAG = "BoutiqueActivity";
    private Map mMap;
    private String[] mMsg;
    private BoutiquePresenter mPresenter;

    @Override
    protected int setResId() {
        return R.layout.activity_boutique;
    }

    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorBackgroundWhite), true);

        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("精品");





    }

    @Override
    protected void initData(Bundle bundle) {
        mMsg = new String[]{"精品游戏","精品软件","角色扮演","生活","理财","社交","男生","女生"};
        mPresenter= new BoutiquePresenter(this);
        mPresenter.getBoutiqueMap();

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setBoutiqueMap(Map map) {
        mMap = map;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < mMsg.length; i++) {

            viewPagerAdapter.addFragment(NormalAppsFragment.newInstance((ArrayList) mMap.get(mMsg[i])));
        }

        mVpContent.setAdapter(viewPagerAdapter);
        mTlNavigation.setupWithViewPager(mVpContent);
        for (int i = 0; i < mMsg.length; i++) {
            TabLayout.Tab tab = mTlNavigation.getTabAt(i);
            assert tab != null;
            tab.setText(mMsg[i]);
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BoutiqueActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, BoutiqueActivity.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

}
