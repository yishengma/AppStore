package piratehat.appstore.contract;

import java.util.Map;

/**
 * Created by PirateHat on 2018/11/23.
 */

public interface IRankContract {
    interface IView{
        void showError(String msg);
         void setRankMap(Map map);
    }

    interface IModel{
        void getRankMap(IPresenter presenter);
    }


    interface IPresenter{
        void showError(String msg);
        void setRankMap(Map map);
        void getRankMap();
    }
}
