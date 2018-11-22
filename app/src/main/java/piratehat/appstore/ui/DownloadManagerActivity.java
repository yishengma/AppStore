package piratehat.appstore.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import piratehat.appstore.Bean.AppDownloadInfo;
import piratehat.appstore.R;
import piratehat.appstore.adapter.DownloadAppAdapter;

public class DownloadManagerActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_download_apps)
    RecyclerView mRvDownloadApps;
    private DownloadAppAdapter mAdapter;
    private List<AppDownloadInfo> mInfos;

    @Override
    protected int setResId() {
        return R.layout.activity_download_manager;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("下载管理");

    }

    @Override
    protected void initData(Bundle bundle) {
        mAdapter = new DownloadAppAdapter(mInfos,this);
        mRvDownloadApps.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        IntentFilter filter = new IntentFilter();
        HashMap<String, DownloadUtil.State> map = DownloadUtil.getInstance().getMap();
        for (String key : map.keySet()) {
            filter.addAction(key);
        }
        registerReceiver(receiver, filter);//
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                HashMap<String, DownloadUtil.State> map = DownloadUtil.getInstance().getMap();
                for (String key : map.keySet()) {

                }
            }

        }
    };


}
