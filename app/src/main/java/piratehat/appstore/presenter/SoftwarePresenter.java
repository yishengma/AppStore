package piratehat.appstore.presenter;

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
    public void setAllApps(List<AppBean> list) {
        ArrayList<AppBean> list1 = new ArrayList<>();
        list1.add(null);
        list1.addAll(list);
        mIView.setAllApps(list1);
    }

    @Override
    public void getAllApps() {
        mIModel.getAllApps(this);

    }
}
