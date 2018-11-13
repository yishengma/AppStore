package piratehat.appstore.contract;

import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/11/13.
 */

public interface IGameContract {

    interface  IView{
        void setAllApps(List<AppBean> list);
    }

    interface IModel{
        void getAllApps(IPresenter presenter);
    }

    interface IPresenter{
        void setAllApps(List<AppBean> list);
        void getAllApps();
    }
}
