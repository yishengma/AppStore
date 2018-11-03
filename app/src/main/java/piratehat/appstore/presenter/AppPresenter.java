package piratehat.appstore.presenter;

import com.shizhefei.mvc.IAsyncDataSource;

import java.util.ArrayList;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.contract.IAppsContract;
import piratehat.appstore.module.CommonAppsModel;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public class AppPresenter implements IAppsContract.IPresenter {

    private IAppsContract.IView mIView;
    private IAppsContract.IModel mIModel;
    public AppPresenter(IAppsContract.IView IView,String category) {
        mIView = IView;
        mIModel =new CommonAppsModel(category);
    }

    @Override
    public void setResult(ArrayList<AppBean> beans) {
        mIView.setResult(beans);
    }

    @Override
    public void getCategory(String category) {
        mIModel.getCategory(this,category);
    }

    @Override
    public void showError(String msg) {
        mIView.showError(msg);
    }

    @Override
    public IAsyncDataSource getAppsData(String category) {
        return mIModel;
    }
}
