package piratehat.appstore.presenter;

import android.content.Context;

import com.yaoxiaowen.download.DownloadHelper;

import java.io.File;

import piratehat.appstore.Bean.AppDetailBean;
import piratehat.appstore.app.App;
import piratehat.appstore.config.Download;
import piratehat.appstore.contract.IAppDetailsContract;
import piratehat.appstore.contract.IAppsContract;
import piratehat.appstore.module.AppDetailModel;
import piratehat.appstore.ui.AppDetailsActivity;
import piratehat.appstore.ui.DownloadUtil;

/**
 * Created by PirateHat on 2018/11/20.
 */

public class AppDetailPresenter implements IAppDetailsContract.IPresenter {

    private IAppDetailsContract.IView mIView;
    private IAppDetailsContract.IModel mIModel;

    public AppDetailPresenter(IAppDetailsContract.IView IView) {
        mIView = IView;
        mIModel = new AppDetailModel();
    }

    @Override
    public void setAppInfo(AppDetailBean appInfo) {
        mIView.setAppInfo(appInfo);
    }

    @Override
    public void getAppDetailInfo(String apkName) {
        mIModel.getAppDetailInfo(this, apkName);
    }

    @Override
    public void showError(String msg) {
        mIView.showError(msg);
    }

    @Override
    public void download(String name, String url) {
        File file = new File(App.getDir(), name+".apk");
        DownloadUtil.getInstance().setState(Download.State.NEW,url);
        DownloadHelper.getInstance().addTask(url,file ,url)
                .submit((Context) mIView);
    }
}
