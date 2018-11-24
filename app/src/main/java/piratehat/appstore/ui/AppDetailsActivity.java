package piratehat.appstore.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import piratehat.appstore.download.DownloadListener;
import piratehat.appstore.download.DownloadService;
import piratehat.appstore.download.DownloadTask;
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
    private String mSize;
    private DownloadService.DownloadBinder mDownloadBinder;
    private DownloadListener mListener;
    private int mState;


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


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
        mState = -2;
        mPresenter = new AppDetailPresenter(this);
        String apkName = bundle.getString("apkName");
        mUrls = new ArrayList<>();
        mAdapter = new ImageAdapter(mUrls, this);
        mVpContent.setAdapter(mAdapter);

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);  //启动服务
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        mPresenter.getAppDetailInfo(apkName);

    }

    @Override
    protected void initListener() {

        if (ContextCompat.checkSelfPermission(AppDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AppDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        mListener = new DownloadListener() {
            @Override
            public void onProgress(int progress) {
                mBtnDownload.setText((progress + "%"));
                mState = -1;
            }

            @Override
            public void onSuccess() {
                mBtnDownload.setText("已完成");
                mState = DownloadTask.TYPE_SUCCESS;
            }

            @Override
            public void onFailed() {
                mBtnDownload.setText("下载失败");
                DownloadService.removeLitener(mDownloadUrl);
                mState = DownloadTask.TYPE_FAILED;
            }

            @Override
            public void onPaused() {
                mBtnDownload.setText("暂停");
                mState = DownloadTask.TYPE_PAUSED;
            }

            @Override
            public void onCanceled() {
                mBtnDownload.setText("下载(" + mSize + ")");
                mState = DownloadTask.TYPE_CANCELED;

            }
        };

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+mState );
                switch (mState) {
                    case -1:
                        mDownloadBinder.pauseDownload();
                        break;
                    case DownloadTask.TYPE_CANCELED:
                        break;
                    case DownloadTask.TYPE_FAILED:
                        mDownloadBinder.startDownload(mDownloadUrl,mName);
                        break;
                    case DownloadTask.TYPE_SUCCESS:
                        break;
                    case DownloadTask.TYPE_PAUSED:
                        mDownloadBinder.startDownload(mDownloadUrl,mName);
                        break;
                    default:
                        DownloadService.addListener(mDownloadUrl, mListener);
                        mDownloadBinder.startDownload(mDownloadUrl,mName);
                        break;
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
        mSize = appInfo.getSize();
        mBtnDownload.setText("下载(" + mSize + ")");
        mDownloadUrl = appInfo.getDownloadUrl();
        mName = appInfo.getName();

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


    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
    }
}
