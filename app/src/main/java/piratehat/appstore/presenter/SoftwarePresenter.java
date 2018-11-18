package piratehat.appstore.presenter;

import com.shizhefei.mvc.IAsyncDataSource;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.contract.IGameContract;
import piratehat.appstore.contract.ISoftwareContract;
import piratehat.appstore.module.SoftwareModel;

/**
 * Created by PirateHat on 2018/11/13.
 */

public class SoftwarePresenter implements ISoftwareContract.IPresenter {

    private ISoftwareContract.IView mIView;
    private ISoftwareContract.IModel mIModel;

    public SoftwarePresenter(ISoftwareContract.IView IView) {
        mIView = IView;
        mIModel = new SoftwareModel();
    }

    @Override
    public void setAppsList(ArrayList<AppBean> beans) {

        mIView.setAllApps(beans);
    }

    @Override
    public void getAppsList() {
        mIModel.getAllApps(this);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public IAsyncDataSource loadMore() {
        return mIModel;
    }


}
