package piratehat.appstore.presenter;

import java.util.Map;

import piratehat.appstore.contract.IBoutiqueContract;
import piratehat.appstore.module.BoutiqueModel;

/**
 * Created by PirateHat on 2018/11/23.
 */

public class BoutiquePresenter implements IBoutiqueContract.IPresenter {

    private IBoutiqueContract.IView mIView;
    private IBoutiqueContract.IModel mIModel;

    public BoutiquePresenter(IBoutiqueContract.IView IView) {
        mIView = IView;
        mIModel = new BoutiqueModel();
    }

    @Override
    public void showError() {
        mIView.showError();
    }

    @Override
    public void setBoutiqueMap(Map map) {
        mIView.setBoutiqueMap(map);
    }

    @Override
    public void getBoutiqueMap() {
        mIModel.getBoutiqueMap(this);
    }
}
