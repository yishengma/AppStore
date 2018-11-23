package piratehat.appstore.contract;

import android.net.IpPrefix;

import java.util.Map;

/**
 * Created by PirateHat on 2018/11/23.
 */

public interface IBoutiqueContract  {

    interface IView{
        void showError();
        void setBoutiqueMap(Map map);
    }

    interface IModel{
        void getBoutiqueMap(IPresenter presenter);
    }

    interface IPresenter{
        void showError();
        void setBoutiqueMap(Map map);
        void getBoutiqueMap();
    }
}
