package piratehat.appstore.fragment;

import android.annotation.SuppressLint;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import piratehat.appstore.R;
import piratehat.appstore.app.App;
import piratehat.appstore.config.Constant;
import piratehat.appstore.ui.ApkActivity;

import static piratehat.appstore.config.Constant.CLEAR_DOWN;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class ManagerFragment extends BaseFragment {

    @BindView(R.id.tv_cache)
    TextView mTvCache;
    @BindView(R.id.tv_files)
    TextView mTvFiles;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_apk)
    Button mBtnApk;


    private PackageManager mPackageManager;
    private ExecutorService mSingleExecutor;
    private long mCacheMem;
    private FetchThread mFetchThread;
    private ClearThread mClearThread;
    private Handler mHandler;
    private boolean mHasClear;
    private volatile boolean mWait;

    private static final String TAG = "ManagerFragment";

    // 不同的线程，onGetStatsCompleted 是在 Binder 线程池里运行的
//12-30 16:14:05.050 23717-23717/piratehat.appstore E/ManagerFragment: initData: main
//12-30 16:14:18.699 23717-24595/piratehat.appstore E/ManagerFragment: run: pool-5-thread-1
 //12-30 16:14:19.558 23717-23738/piratehat.appstore E/ManagerFragment: onGetStatsCompleted: Binder:23717_2
 //12-30 16:14:19.609 23717-23754/piratehat.appstore E/ManagerFragment: onGetStatsCompleted: Binder:23717_3

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_manager;
    }

    @Override
    protected void initData(Bundle bundle) {
              Log.e(TAG, "initData: "+Thread.currentThread().getName() );
        mPackageManager = mActivity.getPackageManager();
        mHandler = new CacheHandler(this);
        mSingleExecutor = Executors.newSingleThreadExecutor();
        mHasClear = false;
        mWait = true;

    }


    @Override
    protected void initView() {
        mTvCache.setText("缓存清理");

    }

    @Override
    protected void initListener() {
        mTvCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHasClear) {
                    mTvCache.setEnabled(false);
                    fetchAppCache();
                    mHasClear = true;
                } else {
                    mHasClear = false;
                    clearAppCache();
                    Toast.makeText(mActivity, "没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApkActivity.actionStart(mActivity,ApkActivity.class);
            }
        });
    }

    private void fetchAppCache() {
        mFetchThread = new FetchThread();
        mSingleExecutor.execute(mFetchThread);
    }

    private void clearAppCache() {
        mClearThread = new ClearThread();
        mSingleExecutor.execute(mClearThread);
    }

    @SuppressLint("PrivateApi")
    private void getCacheSize(PackageInfo packageInfo) {
        Method method;
        try {
            method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class,//反射获取方法
                    IPackageStatsObserver.class);
            method.invoke(mPackageManager, packageInfo.packageName, new PackageDataObserver(this));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("PrivateApi")
    private void cleanAll() {
        try {
            Method method = PackageManager.class.getDeclaredMethod("freeStorageAndNotify", String.class, long.class, IPackageDataObserver.class);
            method.invoke(mPackageManager, null, Long.MAX_VALUE, new CleanCacheObserver(this));//清空缓存
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }



    private class FetchThread extends Thread {
        @Override
        public void run() {
            Log.e(TAG, "run: "+Thread.currentThread().getName() );
            List<PackageInfo> packageInfoList = mPackageManager.getInstalledPackages(0);
            for (PackageInfo packageInfo : packageInfoList) {
                getCacheSize(packageInfo);
                for (; ; ) {
                    if (!mWait) {
                        break;
                    }
                }
                mWait = true;
                Message message = mHandler.obtainMessage();
                message.what = Constant.RUNNING;
                message.obj = packageInfo;
                mHandler.sendMessage(message);
            }
            mHandler.sendEmptyMessage(Constant.DOWN);
        }
    }

    private class ClearThread extends Thread {
        @Override
        public void run() {

            cleanAll();
        }
    }

    private static class CacheHandler extends Handler {
        private WeakReference<ManagerFragment> mReference;

        CacheHandler(ManagerFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            ManagerFragment managerFragment;
            if ((managerFragment = mReference.get()) == null) {
                return;
            }
            switch (msg.what) {
                case Constant.DOWN:
                    managerFragment.mTvCache.setEnabled(true);
                    managerFragment.mTvFiles.setText("扫描完全.");
                    break;
                case Constant.RUNNING:
                    PackageInfo packageInfo = (PackageInfo) msg.obj;
                    managerFragment.mTvFiles.setText(String.format("正在扫描%s", packageInfo.packageName));
                    String[] str = Formatter.formatFileSize(managerFragment.mActivity, managerFragment.mCacheMem).split(" ");
                    managerFragment.mTvCache.setText(str[0] + (str.length > 1 ? str[1] : ""));

                    break;
                case Constant.CLEAR_DOWN:
                    managerFragment.mTvFiles.setText("");
                    managerFragment.mTvCache.setText("清理完成！");
                    break;
                default:
                    break;
            }


        }
    }

    private static class PackageDataObserver extends IPackageStatsObserver.Stub {
        private WeakReference<ManagerFragment> mReference;

        PackageDataObserver(ManagerFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            Log.e(TAG, "onGetStatsCompleted: "+Thread.currentThread().getName());
            ManagerFragment fragment = mReference.get();
            if (fragment == null) {
                return;
            }
            long cacheSize = pStats.cacheSize;
            if (cacheSize >= 0) {
                fragment.mCacheMem += cacheSize;
            }
            fragment.mWait = false;

        }
    }

    private static class CleanCacheObserver extends IPackageDataObserver.Stub {
        private WeakReference<ManagerFragment> mReference;

        CleanCacheObserver(ManagerFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {
            mReference.get().mHandler.sendEmptyMessage(CLEAR_DOWN);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getmRerWatcher().watch(this);
    }
}
