package piratehat.appstore.presenter;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.contract.IGameContract;
import piratehat.appstore.module.GameModel;

/**
 * Created by PirateHat on 2018/11/13.
 */

public class GamePresenter implements IGameContract.IPresenter {

    private IGameContract.IView mIView;
    private IGameContract.IModel mIModel;

    public GamePresenter(IGameContract.IView IView) {
        mIView = IView;
        mIModel = new GameModel();
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
