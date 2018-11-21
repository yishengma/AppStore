package piratehat.appstore.contract;

import android.content.Context;

import piratehat.appstore.Bean.AppDetailBean;

/**
 * Created by PirateHat on 2018/11/20.
 */

public interface IAppDetailsContract {
    interface IView {
        void setAppInfo(AppDetailBean appInfo);
        void showError(String msg);
    }


    interface IModel {
        void getAppDetailInfo(IPresenter presenter,String apkName);

    }

    interface IPresenter {
        void setAppInfo(AppDetailBean appInfo);
        void showError(String msg);
        void getAppDetailInfo(String apkName);
        void download(String name,String url);

    }


}
