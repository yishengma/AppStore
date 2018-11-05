package piratehat.appstore.presenter;

import com.shizhefei.mvc.IAsyncDataSource;

import java.util.ArrayList;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.contract.ITencentContract;
import piratehat.appstore.module.TencentModel;

/**
 * Created by PirateHat on 2018/11/5.
 */

public class TencentPresenter implements ITencentContract.IPresenter {
    private ITencentContract.IView mIView;
    private ITencentContract.IModel mIModel;

    public TencentPresenter(ITencentContract.IView IView) {
        mIView = IView;
        mIModel = new TencentModel();
    }

    @Override
    public void setAppsList(ArrayList<AppBean> beans) {
          mIView.setAppsList(beans);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void getAppsList() {
        mIModel.getAppsList(this);
    }

    @Override
    public IAsyncDataSource loadMore() {
        return mIModel;
    }
}
