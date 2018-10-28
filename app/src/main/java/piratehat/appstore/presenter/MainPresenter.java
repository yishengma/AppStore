package piratehat.appstore.presenter;

import java.util.ArrayList;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.Bean.BannerBean;
import piratehat.appstore.contract.IMainContract;
import piratehat.appstore.module.MainModule;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public class MainPresenter implements IMainContract.IPresenter {

    private IMainContract.IView mIView;
    private IMainContract.IModel mIModel;

    public MainPresenter(IMainContract.IView IView) {
        mIView = IView;
        mIModel = new MainModule();
    }

    @Override
    public void getMainPage() {
        mIModel.getMainPage(this);
        mIModel.getAllApps(this);
    }

    @Override
    public void setBanner(ArrayList<BannerBean> beans) {
        ArrayList<String> titles = new ArrayList<>();
        int size = beans.size();
        for (int i = 0; i < size; i++) {
            titles.add(beans.get(i).getName());
        }
        mIView.setBanner(beans, titles);
    }

    @Override
    public void setAppList(ArrayList<AppBean> appBeans) {
        mIView.setAppList(appBeans);
    }

    @Override
    public void showError(String msg) {
        mIView.showError(msg);
    }
}
