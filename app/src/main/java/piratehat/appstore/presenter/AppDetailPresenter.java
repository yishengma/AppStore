package piratehat.appstore.presenter;

import piratehat.appstore.Bean.AppDetailBean;
import piratehat.appstore.contract.IAppDetailsContract;
import piratehat.appstore.contract.IAppsContract;
import piratehat.appstore.module.AppDetailModel;

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
}
