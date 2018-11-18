package piratehat.appstore.presenter;

import android.util.Log;

import com.shizhefei.mvc.IAsyncDataSource;

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
    private static final String TAG = "GamePresenter";

    public GamePresenter(IGameContract.IView IView) {
        mIView = IView;
        mIModel = new GameModel();
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
