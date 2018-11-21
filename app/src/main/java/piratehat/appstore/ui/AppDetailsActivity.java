package piratehat.appstore.ui;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yaoxiaowen.download.DownloadConstant;
import com.yaoxiaowen.download.DownloadHelper;
import com.yaoxiaowen.download.DownloadStatus;
import com.yaoxiaowen.download.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import piratehat.appstore.Bean.AppDetailBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.ImageAdapter;


import piratehat.appstore.config.Download;
import piratehat.appstore.contract.IAppDetailsContract;
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
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.btn_download)
    Button mBtnDownload;

    private AppDetailPresenter mPresenter;
    private ImageAdapter mAdapter;
    private List<String> mUrls;
    private String mDownloadUrl;
    private String mName;

    private static final String TAG = "AppDetailsActivity";
    @Override
    protected int setResId() {
        return R.layout.activity_app_details;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    @Override
    protected void initData(Bundle bundle) {
        mPresenter = new AppDetailPresenter(this);
        String apkName = bundle.getString("apkName");
        mUrls = new ArrayList<>();
        mAdapter = new ImageAdapter(mUrls, this);
        mVpContent.setAdapter(mAdapter);
        //广播不要忘记注册和反注册。

        mPresenter.getAppDetailInfo(apkName);

    }

    @Override
    protected void initListener() {

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDownloadUrl) && DownloadUtil.getInstance().getState(mDownloadUrl).getState() != Download.State.FINISHED) {
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(mDownloadUrl);
                    registerReceiver(receiver, filter);
                     mPresenter.download(mName,mDownloadUrl);


                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();

            }

        }
        return false;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setAppInfo(AppDetailBean appInfo) {
        Glide.with(this).load(appInfo.getIcon()).into(mImvIcon);
        mTvName.setText(appInfo.getName());
        mToolBar.setTitle(appInfo.getName());
        mTvScore.setText(appInfo.getScore());
        mTvHot.setText(appInfo.getBaseInfo());
        mTvInfo.setText(appInfo.getInfo());
        mTvIntroduction.setText(appInfo.getDetailInfo());
        mUrls.addAll(appInfo.getImageList());
        mAdapter.notifyDataSetChanged();
        mBtnDownload.setText("下载(" + appInfo.getSize() + ")");
        mDownloadUrl = appInfo.getDownloadUrl();
        mName= appInfo.getName();

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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                HashMap<String, DownloadUtil.State> map = DownloadUtil.getInstance().getMap();
                for (String key : map.keySet()) {
                    updateView(intent, key);
                }
            }

        }
    };

    @SuppressLint("SetTextI18n")
    private void updateView(Intent intent, String key) {
        if (key.equals(mDownloadUrl)) {
            com.yaoxiaowen.download.FileInfo fileInfo =
                    (FileInfo) intent.getSerializableExtra(
                            DownloadConstant.EXTRA_INTENT_DOWNLOAD);

            switch (fileInfo.getDownloadStatus()) {
                case DownloadStatus.COMPLETE:
                    mBtnDownload.setText("已下载");
                    DownloadUtil.getInstance().setState(Download.State.FINISHED,mDownloadUrl);
                    break;
                case DownloadStatus.LOADING:
                    float pro = (float) (fileInfo.getDownloadLocation() * 1.0 / fileInfo.getSize());
                    int progress = (int) (pro * 100);
                    mBtnDownload.setText(progress + "%");
                    break;
                case DownloadStatus.PAUSE:
                    mBtnDownload.setText("暂停");
                    break;
                case DownloadStatus.FAIL:
                    mBtnDownload.setText("下载失败");
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}
