package piratehat.appstore.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

import piratehat.appstore.R;
import piratehat.appstore.adapter.ApkAdapter;
import piratehat.appstore.app.App;

public class ApkActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    private ArrayList<String> mList;
    private ApkAdapter mAdapter;
    private static final String TAG = "ApkActivity";

    @Override
    protected int setResId() {
        return R.layout.activity_apk;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("安装包管理");
    }

    @Override
    protected void initData(Bundle bundle) {
        mRvApps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList = new ArrayList<>();
        mList.addAll(getFiles(App.getDir()));
        mAdapter = new ApkAdapter(this, mList);
        mRvApps.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;

    }

    @Override
    protected void initListener() {

    }

    public static ArrayList<String> getFiles(File file) {
        ArrayList<String> files = new ArrayList<>();
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {

            if (tempList[i].isFile()) {

                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
            }
        }
        return files;
    }

}
