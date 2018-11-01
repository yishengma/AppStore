package piratehat.appstore.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.Bean.BannerBean;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public interface IMainContract {

    interface IView{
        void setBanner(ArrayList<BannerBean> beans,ArrayList<String> titles);
        void setRankApps(Map<String,List<AppBean>> beans);

        void showError(String msg);
    }

    interface IModel{
        void getMainPage(IPresenter presenter);


    }

    interface IPresenter{
        void getMainPage();
        void setBanner(ArrayList<BannerBean> beans);
        void setRankApps(Map<String,List<AppBean>> beans);
        void showError(String msg);
    }
}
