package piratehat.appstore.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;


import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


import piratehat.appstore.R;
import piratehat.appstore.adapter.ViewPagerAdapter;
import piratehat.appstore.contract.IRankContract;
import piratehat.appstore.fragment.RankFragment;
import piratehat.appstore.presenter.RankPresenter;

public class RankActivity extends AppCompatActivity implements IRankContract.IView{
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tl_navigation)
    TabLayout mTlNavigation;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;


    private static final String TAG = "RankActivity";
    private Map mAppsMap;
    public static final String[] sRANK = new String[]{"游戏下载", "软件下载", "动作游戏", "棋牌游戏", "社交软件"};
    private RankPresenter mPresenter;

    //应用下载
    //游戏下载
    //动作游戏
    //棋牌游戏
    //社交软件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorBackgroundWhite), true);

        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("榜单");




    }

    private void initData() {
        mPresenter = new RankPresenter(this);
        mPresenter.getRankMap();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_search:

                break;
            case R.id.item_download:

                break;
            case android.R.id.home: {
                finish();

            }

        }
        return false;

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRankMap(Map map) {

        mAppsMap = map;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < sRANK.length; i++) {
            viewPagerAdapter.addFragment(RankFragment.newInstance((ArrayList) mAppsMap.get(sRANK[i])));
        }

        mVpContent.setAdapter(viewPagerAdapter);
        mTlNavigation.setupWithViewPager(mVpContent);

        for (int i = 0; i < sRANK.length; i++) {
            mTlNavigation.getTabAt(i).setText(sRANK[i]);
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RankActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, RankActivity.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }
}
